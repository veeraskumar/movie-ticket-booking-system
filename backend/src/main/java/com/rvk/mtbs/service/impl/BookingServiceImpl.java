package com.rvk.mtbs.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.entity.Booking;
import com.rvk.mtbs.entity.BookingSeat;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.BookingStatus;
import com.rvk.mtbs.exception.BookingNotFoundException;
import com.rvk.mtbs.exception.InvalidSeatConfigurationException;
import com.rvk.mtbs.exception.ShowNotFoundException;
import com.rvk.mtbs.mapper.BookingMapper;
import com.rvk.mtbs.repository.BookingRepository;
import com.rvk.mtbs.repository.BookingSeatRepository;
import com.rvk.mtbs.repository.ShowRepository;
import com.rvk.mtbs.service.BookingService;
import com.rvk.mtbs.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final BookingSeatRepository bookingSeatRepository;
	private final ShowRepository showRepository;
	private final EmailService emailService;

	@Override
	@Transactional
	public BookingResponse create(BookingRequest request, User user) {

		Show show = showRepository.findById(request.showId())
				.orElseThrow(() -> new ShowNotFoundException("Show not Found"));

		for (Integer seat : request.seats()) {
			if (seat <= 0 || seat > show.getTotalSeats()) {
				throw new InvalidSeatConfigurationException("Seat " + seat + " does not exist");
			}
		}

		Set<Integer> uniqueSeats = new HashSet<>(request.seats());
		if (uniqueSeats.size() != request.seats().size()) {
			throw new InvalidSeatConfigurationException("Duplicate seats selected");
		}

		List<Integer> alreadyBooked = bookingSeatRepository.findBookedSeatNumbers(show.getId());
		for (Integer seat : request.seats()) {
			if (alreadyBooked.contains(seat)) {
				throw new InvalidSeatConfigurationException("Seat " + seat + " is already booked");
			}
		}

		int totalPrice = 0;
		for (Integer seat : request.seats()) {
			if (seat <= show.getEconomySeatTo())
				totalPrice += show.getEconomySeatPrice();
			else if (seat <= show.getPremiumSeatTo())
				totalPrice += show.getPremiumSeatPrice();
			else
				totalPrice += show.getReclinerSeatPrice();
		}

		Booking booking = BookingMapper.toEntity(request.seats(), totalPrice, show, user);

		try {
			Booking savedBooking = bookingRepository.save(booking);
			emailService.sendBookingConfirmation(user.getEmail(), user.getName(), show.getMovieName(),
					request.seats().toString(), totalPrice);
			return BookingMapper.toResponse(savedBooking);
		} catch (DataIntegrityViolationException ex) {
			throw new InvalidSeatConfigurationException(
					"One or more selected seats were just booked by someone else. Please try again.");
		}
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
		
		List<Integer> seats = booking.getSeats().stream().map(BookingSeat::getSeatNumber).toList();
		
		emailService.sendBookingCancellation(booking.getUser().getEmail(), booking.getUser().getName(),
				booking.getShow().getMovieName(), seats.toString(), booking.getTotalPrice());

		return BookingMapper.toResponse(booking);
	}
}
