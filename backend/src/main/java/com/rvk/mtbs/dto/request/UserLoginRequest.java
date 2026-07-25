package com.rvk.mtbs.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequest(@Email(message = "Email is required") String email,
		@NotBlank(message = "Password is required") 
		@Size(min = 8, message = "please fill atleast 8 character") String password) {
}
