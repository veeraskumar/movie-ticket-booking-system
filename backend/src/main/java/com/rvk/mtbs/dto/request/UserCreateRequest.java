package com.rvk.mtbs.dto.request;

import com.rvk.mtbs.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
		@NotBlank(message = "Name is required") @Size(min = 3, message = "fill atleast 3 characters") String name,
		@NotBlank(message = "Email is required") @Email(message = "Email is required") String email,
		@NotBlank(message = "Password is required") @Size(min = 8, message = "fill atleast 8 characters for password") String password,
		@NotNull(message = "Role is required") Role role) {
}