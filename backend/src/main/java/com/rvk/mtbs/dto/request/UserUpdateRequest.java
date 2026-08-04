package com.rvk.mtbs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
		@NotBlank(message = "Name is required") @Size(min = 3, message = "fill atleast 3 characters") String name,
		@Email(message = "Email is required") String email) {
}