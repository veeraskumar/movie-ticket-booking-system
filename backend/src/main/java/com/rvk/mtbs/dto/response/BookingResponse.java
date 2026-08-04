package com.rvk.mtbs.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.rvk.mtbs.enums.BookingStatus;

public record BookingResponse(
	    Long id,
	    Long showId,
	    String movieName,
	    List<Integer> seatNumbers,
	    Integer totalPrice,
	    BookingStatus status,
	    LocalDateTime createdAt
	) {}