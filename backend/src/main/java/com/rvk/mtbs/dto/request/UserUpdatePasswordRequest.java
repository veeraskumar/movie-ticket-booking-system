package com.rvk.mtbs.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordRequest(
		@NotBlank(message = "Password is required") @Size(min = 8, message = "fill atleast 8 characters for password") String oldPassword,
		@NotBlank(message = "Password is required") @Size(min = 8, message = "fill atleast 8 characters for password") String newPassword,
		@NotBlank(message = "Password is required") @Size(min = 8, message = "fill atleast 8 characters for password") String confirmNewPassword) {
}
