package com.rvk.mtbs.dto.response;

import java.time.LocalDateTime;

import com.rvk.mtbs.enums.Role;
import com.rvk.mtbs.enums.UserStatus;

public record UserResponse(
	    Long id,
	    String name,
	    String email,
	    Role role,
	    UserStatus status,
	    LocalDateTime createdAt
	) {}
