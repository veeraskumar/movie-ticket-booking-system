package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;

import com.rvk.mtbs.dto.response.ErrorResponse;

public final class ErrorMapper {

	private ErrorMapper() {
	}

	public static ErrorResponse toResponse(String message, int status) {
		return new ErrorResponse(message, status, LocalDateTime.now());
	}

}
