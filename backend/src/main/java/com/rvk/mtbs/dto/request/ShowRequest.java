package com.rvk.mtbs.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ShowRequest(@NotBlank(message = "Movie name is required") @Size(min = 2, max = 100) String movieName,
		
		@NotNull(message = "Room number is required") @Positive Integer roomNumber,
		
		@NotNull(message = "Start time is required") @Future LocalDateTime startTime,
		
		@NotNull(message = "Duration is required") @Min(10) Integer durationMinutes,
		
		@NotNull(message = "Total seats are required") 
		@Positive(message = "Total seats must be greater than 0") Integer totalSeats,
		
		@NotNull(message = "Economy seat limit is required") 
		@Positive(message = "Total seats must be greater than 0") Integer economySeatTo,
		
		@NotNull(message = "Economy seat price is required") 
		@Positive(message = "Total seats must be greater than 0") Integer economySeatPrice,
		
		@NotNull(message = "Premium seat limit is required") 
		@Positive(message = "Total seats must be greater than 0") Integer premiumSeatTo,
		
		@NotNull(message = "Premium seat price is required") 
		@Positive(message = "Total seats must be greater than 0") Integer premiumSeatPrice,
		
		@NotNull(message = "Recliner seat limit is required") 
		@Positive(message = "Total seats must be greater than 0") Integer reclinerSeatTo,
		
		@NotNull(message = "Recliner seat price is required") 
		@Positive(message = "Total seats must be greater than 0") Integer reclinerSeatPrice,
		
		@NotNull(message = "Theater ID is required") @Positive Long theaterId) {
}
