package com.rvk.mtbs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConfirmPasswordRequest(
		@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
		@NotNull(message = "Code is required") Integer code,
		@NotBlank(message = "Password is required") @Size(min = 8, message = "fill atleast 8 characters for password") String newPassword,
		@NotBlank(message = "Password is required") String confirmPassword) {
}