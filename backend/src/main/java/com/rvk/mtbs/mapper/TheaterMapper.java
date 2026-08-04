package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;
import com.rvk.mtbs.entity.Theater;
import com.rvk.mtbs.entity.User;

public final class TheaterMapper {

	private TheaterMapper() {
	}

	public static TheaterResponse toResponse(Theater theater) {
		return new TheaterResponse(theater.getId(), theater.getName(), theater.getNoOfRooms(), theater.getCity(),
				theater.getAddress(), theater.getGoogleMapUrl(), theater.getStatus());
	}

	public static Theater toEntity(TheaterRequest request, User user) {
		Theater theater = new Theater();

		theater.setName(request.name());
		theater.setNoOfRooms(request.noOfRoom());
		theater.setCity(request.city());
		theater.setAddress(request.address());
		theater.setGoogleMapUrl(request.googleMapUrl());
		theater.setStatus(request.status());
		theater.setCreatedAt(LocalDateTime.now());
		theater.setUpdatedAt(LocalDateTime.now());
		theater.setOwner(user);

		return theater;
	}
}
