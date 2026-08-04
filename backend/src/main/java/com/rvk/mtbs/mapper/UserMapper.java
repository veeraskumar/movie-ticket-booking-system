package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;

import com.rvk.mtbs.dto.request.UserCreateRequest;
import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.Role;
import com.rvk.mtbs.enums.UserStatus;

public final class UserMapper {

	private UserMapper() {
	}

	public static UserResponse toResponse(User user) {
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getStatus(),
				user.getCreatedAt());
	}

	public static User toEntity(UserRegisterRequest request) {
		User user = new User();

		user.setName(request.name());
		user.setEmail(request.email());
		user.setRole(Role.USER);
		user.setStatus(UserStatus.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		return user;
	}

	public static User toEntity(UserCreateRequest request) {
		User user = new User();

		user.setName(request.name());
		user.setEmail(request.email());
		user.setStatus(UserStatus.ACTIVE);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		return user;
	}

}
