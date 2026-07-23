package com.rvk.mtbs.dto.response;

import java.time.LocalDateTime;

public record ShowResponse(
	    Long id,
	    String movieName,
	    Integer roomNumber,
	    LocalDateTime startTime,
	    Integer durationMinutes,
	    Integer totalSeats,

	    Integer economySeatTo,
	    Integer economySeatPrice,

	    Integer premiumSeatTo,
	    Integer premiumSeatPrice,

	    Integer reclinerSeatTo,
	    Integer reclinerSeatPrice,

	    String theaterName
	) {}
