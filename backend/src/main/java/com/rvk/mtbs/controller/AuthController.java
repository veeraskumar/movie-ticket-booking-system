package com.rvk.mtbs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.ConfirmPasswordRequest;
import com.rvk.mtbs.dto.request.ForgotPasswordRequest;
import com.rvk.mtbs.dto.request.LoginRequest;
import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.response.LoginResponse;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
		authService.forgotPassword(request.email());
		return ResponseEntity.ok().build();
	}

	@PostMapping("/confirm-password")
	public ResponseEntity<Void> confirmPassword(@Valid @RequestBody ConfirmPasswordRequest request) {
		authService.confirmPassword(request.email(), request.code(), request.newPassword(), request.confirmPassword());
		return ResponseEntity.ok().build();
	}
}