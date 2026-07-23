package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.entity.Booking;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.BookingStatus;

public final class BookingMapper {

	private BookingMapper() {
	}

	public static BookingResponse toResponse(Booking booking) {
		return new BookingResponse(booking.getId(), booking.getShow().getMovieName(), booking.getSeatNumbers(),
				booking.getTotalPrice(), booking.getStatus(), booking.getCreatedAt(), booking.getUser().getName());
	}

	public static Booking toEntity(BookingRequest request,String seats,int totalPrice,Show show, User user) {
		Booking booking = new Booking();

		booking.setTotalPrice(totalPrice);
	    booking.setStatus(BookingStatus.CONFIRMED);
		booking.setSeatNumbers(seats);
		booking.setCreatedAt(LocalDateTime.now());
		booking.setShow(show);
		booking.setUser(user);

		return booking;
	}
}
