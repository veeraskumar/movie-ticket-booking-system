package com.rvk.mtbs.dto.request;

import com.rvk.mtbs.enums.City;
import com.rvk.mtbs.enums.TheaterStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TheaterRequest(
		@NotBlank(message = "Theater name is required") @Size(min = 3, message = "fill atleast 3 characters") String name,
		@NotNull(message = "Number of Room is required") Integer noOfRoom,
		@NotNull(message = "City is required") City city,
		@NotBlank(message = "Address is required") @Size(min = 3, message = "fill atleast 3 characters") String address,
		@NotBlank(message = "Google Maps URL is required") @Size(min = 3, message = "fill atleast 3 characters") String googleMapUrl,
		@NotNull(message = "Theater Status is required") TheaterStatus status) {

}
