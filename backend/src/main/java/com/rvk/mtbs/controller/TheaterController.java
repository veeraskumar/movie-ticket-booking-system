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

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;
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
	public ResponseEntity<TheaterResponse> create(@Valid @RequestBody TheaterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(theaterService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<TheaterResponse>> getAll() {
		return ResponseEntity.ok(theaterService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TheaterResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(theaterService.getById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TheaterResponse> update(@PathVariable Long id, @Valid @RequestBody TheaterRequest request) {
		return ResponseEntity.ok(theaterService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		theaterService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
