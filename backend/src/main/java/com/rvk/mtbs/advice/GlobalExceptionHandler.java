package com.rvk.mtbs.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rvk.mtbs.dto.response.ErrorResponse;
import com.rvk.mtbs.exception.BookingNotFoundException;
import com.rvk.mtbs.exception.EmailAlreadyExistsException;
import com.rvk.mtbs.exception.InvalidPasswordException;
import com.rvk.mtbs.exception.InvalidResetCodeException;
import com.rvk.mtbs.exception.InvalidSeatConfigurationException;
import com.rvk.mtbs.exception.PasswordMismatchException;
import com.rvk.mtbs.exception.ShowNotFoundException;
import com.rvk.mtbs.exception.TheaterNotFoundException;
import com.rvk.mtbs.exception.UserNotFoundException;
import com.rvk.mtbs.mapper.ErrorMapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handlerEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.CONFLICT.value()));
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> handlerInvalidPasswordException(InvalidPasswordException exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED.value()));
	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ErrorResponse> handlerPasswordMismatchException(PasswordMismatchException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerUserNotFoundException(UserNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(TheaterNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerTheaterNotFoundException(TheaterNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(ShowNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerShowNotFoundException(ShowNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerBookingNotFoundException(BookingNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(InvalidSeatConfigurationException.class)
	public ResponseEntity<ErrorResponse> handlerInvalidSeatConfigurationException(
			InvalidSeatConfigurationException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<String, String>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ErrorMapper.toResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(
			org.springframework.security.access.AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorMapper
				.toResponse("You don't have permission to perform this action", HttpStatus.FORBIDDEN.value()));
	}
	
	@ExceptionHandler(InvalidResetCodeException.class)
	public ResponseEntity<ErrorResponse> handlerInvalidResetCodeException(InvalidResetCodeException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMapper.toResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}
}
