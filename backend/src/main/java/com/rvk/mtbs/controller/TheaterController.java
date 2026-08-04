package com.rvk.mtbs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;
import com.rvk.mtbs.enums.City;
import com.rvk.mtbs.security.CustomUserDetails;
import com.rvk.mtbs.service.TheaterService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/theaters")
@Tag(name = "Theater")
public class TheaterController {

	private final TheaterService theaterService;

	@PostMapping
	@PreAuthorize("hasAnyRole('OWNER','ADMIN')")
	public ResponseEntity<TheaterResponse> create(@Valid @RequestBody TheaterRequest request,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity.status(HttpStatus.CREATED).body(theaterService.create(request, userDetails.getUser()));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<TheaterResponse>> getAll() {
		return ResponseEntity.ok(theaterService.getAll());
	}

	@GetMapping("/city/{city}")
	public ResponseEntity<List<TheaterResponse>> getAllByCity(@PathVariable City city) {
		return ResponseEntity.ok(theaterService.getAllByCity(city));
	}

	@GetMapping("/owner")
	@PreAuthorize("hasAnyRole('OWNER','ADMIN')")
	public ResponseEntity<List<TheaterResponse>> getAllByOwner(@AuthenticationPrincipal CustomUserDetails UserDetails) {
		return ResponseEntity.ok(theaterService.findByOwnerId(UserDetails.getUser()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TheaterResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(theaterService.getById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isTheaterOwner(#id, authentication)")
	public ResponseEntity<TheaterResponse> update(@PathVariable Long id, @Valid @RequestBody TheaterRequest request) {
		return ResponseEntity.ok(theaterService.update(id, request));
	}

	@PutMapping("/{id}/shutdown")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isTheaterOwner(#id, authentication)")
	public ResponseEntity<TheaterResponse> shutdown(@PathVariable Long id) {
		return ResponseEntity.ok(theaterService.shutdown(id));
	}

}
