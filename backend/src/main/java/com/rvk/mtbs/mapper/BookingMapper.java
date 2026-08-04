package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.entity.Booking;
import com.rvk.mtbs.entity.BookingSeat;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.BookingStatus;

public final class BookingMapper {

	private BookingMapper() {
	}

	public static BookingResponse toResponse(Booking booking) {
		List<Integer> seatNumbers = booking.getSeats().stream().map(BookingSeat::getSeatNumber).toList();

		return new BookingResponse(booking.getId(), booking.getShow().getId(),booking.getShow().getMovieName(), seatNumbers,
				booking.getTotalPrice(), booking.getStatus(), booking.getCreatedAt());
	}

	public static Booking toEntity(List<Integer> seatNumbers, int totalPrice, Show show, User user) {
		Booking booking = new Booking();

		booking.setTotalPrice(totalPrice);
		booking.setStatus(BookingStatus.CONFIRMED);
		booking.setCreatedAt(LocalDateTime.now());
		booking.setShow(show);
		booking.setUser(user);

		List<BookingSeat> seats = seatNumbers.stream().map(seatNumber -> {
			BookingSeat bookingSeat = new BookingSeat();
			bookingSeat.setSeatNumber(seatNumber);
			bookingSeat.setShow(show);
			bookingSeat.setBooking(booking);
			return bookingSeat;
		}).toList();

		booking.getSeats().addAll(seats);

		return booking;
	}
}