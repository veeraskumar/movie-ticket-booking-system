package com.rvk.mtbs.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.entity.Booking;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.BookingStatus;
import com.rvk.mtbs.exception.BookingNotFoundException;
import com.rvk.mtbs.exception.InvalidSeatConfigurationException;
import com.rvk.mtbs.exception.ShowNotFoundException;
import com.rvk.mtbs.exception.UserNotFoundException;
import com.rvk.mtbs.mapper.BookingMapper;
import com.rvk.mtbs.repository.BookingRepository;
import com.rvk.mtbs.repository.ShowRepository;
import com.rvk.mtbs.repository.UserRepository;
import com.rvk.mtbs.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final ShowRepository showRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public BookingResponse create(BookingRequest request) {

		Show show = showRepository.findById(request.showId())
				.orElseThrow(() -> new ShowNotFoundException("Show not Found"));
		User user = userRepository.findById(request.userId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		String seats = request.seats().stream().map(String::valueOf).collect(Collectors.joining(","));
		int totalPrice = 0;

		for (Integer seat : request.seats()) {
			if (seat <= 0 || seat > show.getTotalSeats()) {
				throw new InvalidSeatConfigurationException("Seat " + seat + " does not exist");
			}
		}

		Set<Integer> uniqueSeats = new HashSet<>(request.seats());

		if (uniqueSeats.size() != request.seats().size()) {
			throw new InvalidSeatConfigurationException("Duplicate seats selected");
		}

		for (Integer seat : request.seats()) {
			if (seat <= show.getEconomySeatTo())
				totalPrice += show.getEconomySeatPrice();
			else if (seat <= show.getPremiumSeatTo())
				totalPrice += show.getPremiumSeatPrice();
			else
				totalPrice += show.getReclinerSeatPrice();
		}

		Booking booking = BookingMapper.toEntity(request, seats, totalPrice, show, user);

		Booking savedBooking = bookingRepository.save(booking);

		return BookingMapper.toResponse(savedBooking);
	}

	@Override
	public List<BookingResponse> getAll() {
		return bookingRepository.findAll().stream().map(BookingMapper::toResponse).toList();
	}

	@Override
	public BookingResponse getById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));
		return BookingMapper.toResponse(booking);
	}

	@Override
	@Transactional
	public BookingResponse cancel(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found"));
		booking.setStatus(BookingStatus.CANCELLED);
		return BookingMapper.toResponse(booking);
	}
}
