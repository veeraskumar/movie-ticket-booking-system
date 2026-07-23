package com.rvk.mtbs.dto.response;

import java.time.LocalDateTime;

import com.rvk.mtbs.enums.BookingStatus;

public record BookingResponse(
	    Long id,
	    String movieName,
	    String seatNumbers,
	    Integer totalPrice,
	    BookingStatus status,
	    LocalDateTime createdAt,
	    String userName
	) {}