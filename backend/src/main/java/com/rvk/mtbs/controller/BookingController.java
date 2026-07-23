package com.rvk.mtbs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.service.BookingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
@Tag(name = "Booking")
public class BookingController {

	private final BookingService bookingService;

	@PostMapping
	public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<BookingResponse>> getAll() {
		return ResponseEntity.ok(bookingService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookingResponse> delete(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.cancel(id));
	}

}
