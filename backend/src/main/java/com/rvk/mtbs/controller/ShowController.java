package com.rvk.mtbs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvk.mtbs.dto.request.ShowRequest;
import com.rvk.mtbs.dto.response.ShowResponse;
import com.rvk.mtbs.service.ShowService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shows")
@Tag(name = "Show")
public class ShowController {

	private final ShowService showService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isTheaterOwner(#request.theaterId(), authentication)")
	public ResponseEntity<ShowResponse> create(@Valid @RequestBody ShowRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(showService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<ShowResponse>> getAll() {
		return ResponseEntity.ok(showService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(showService.getById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isShowsTheaterOwner(#id, authentication)")
	public ResponseEntity<ShowResponse> update(@PathVariable Long id, @Valid @RequestBody ShowRequest request) {
		return ResponseEntity.ok(showService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or @resourceSecurity.isShowsTheaterOwner(#id, authentication)")
	public ResponseEntity<ShowResponse> delete(@PathVariable Long id) {
		showService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
