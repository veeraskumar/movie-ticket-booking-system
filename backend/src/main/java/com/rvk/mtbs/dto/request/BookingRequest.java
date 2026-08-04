package com.rvk.mtbs.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookingRequest(
		@NotNull(message = "Show ID is required") @Positive Long showId,
		@NotEmpty(message = "Seat count is required") List<Integer> seats) {

}
