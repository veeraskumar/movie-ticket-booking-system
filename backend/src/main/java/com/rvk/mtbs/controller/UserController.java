package com.rvk.mtbs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.request.UserUpdatePasswordRequest;
import com.rvk.mtbs.dto.request.UserUpdateRequest;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
		return ResponseEntity.ok(userService.update(id, request));
	}

	@PutMapping("/password/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id,
			@Valid @RequestBody UserUpdatePasswordRequest request) {
		userService.updateUserPassword(id, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
