package com.rvk.mtbs.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;
import com.rvk.mtbs.entity.Theater;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.exception.TheaterNotFoundException;
import com.rvk.mtbs.exception.UserNotFoundException;
import com.rvk.mtbs.mapper.TheaterMapper;
import com.rvk.mtbs.repository.TheaterRepository;
import com.rvk.mtbs.repository.UserRepository;
import com.rvk.mtbs.service.TheaterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TheaterServiceImpl implements TheaterService {

	private final TheaterRepository theaterRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public TheaterResponse create(TheaterRequest request) {
		User user = userRepository.findById(request.userId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Theater theater = TheaterMapper.toEntity(request, user);
		Theater savedTheater = theaterRepository.save(theater);
		return TheaterMapper.toResponse(savedTheater);
	}

	@Override
	public List<TheaterResponse> getAll() {
		return theaterRepository.findAll().stream().map(TheaterMapper::toResponse).toList();
	}

	@Override
	public TheaterResponse getById(Long id) {
		Theater theater = theaterRepository.findById(id)
				.orElseThrow(() -> new TheaterNotFoundException("Theater not found"));
		return TheaterMapper.toResponse(theater);
	}

	@Override
	@Transactional
	public TheaterResponse update(Long id, TheaterRequest request) {
		Theater theater = theaterRepository.findById(id)
				.orElseThrow(() -> new TheaterNotFoundException("Theater not found"));

		theater.setName(request.name());
		theater.setNoOfRooms(request.noOfRoom());
		theater.setCity(request.city());
		theater.setAddress(request.address());
		theater.setGoogleMapUrl(request.googleMapUrl());
		theater.setStatus(request.status());
		theater.setUpdatedAt(LocalDateTime.now());

		Theater savedTheater = theaterRepository.save(theater);
		
		return TheaterMapper.toResponse(savedTheater);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Theater theater = theaterRepository.findById(id)
				.orElseThrow(() -> new TheaterNotFoundException("Theater not found"));
		theaterRepository.delete(theater);
	}
}
