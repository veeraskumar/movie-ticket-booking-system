package com.rvk.mtbs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.UserCreateRequest;
import com.rvk.mtbs.dto.request.UserUpdateRequest;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.security.CustomUserDetails;
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

	@PostMapping("/staff")
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	public ResponseEntity<UserResponse> createStaff(@Valid @RequestBody UserCreateRequest request,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createStaff(request, userDetails.getUser()));
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponse>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}

	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserResponse> getById(@AuthenticationPrincipal CustomUserDetails details) {
		return ResponseEntity.ok(userService.getById(details.getUser().getId()));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isSelf(#id, authentication)")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
		return ResponseEntity.ok(userService.update(id, request));
	}


	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isSelf(#id, authentication)")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
