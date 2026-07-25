package com.rvk.mtbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.UserCreateRequest;
import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.request.UserUpdatePasswordRequest;
import com.rvk.mtbs.dto.request.UserUpdateRequest;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.Role;
import com.rvk.mtbs.enums.UserStatus;
import com.rvk.mtbs.exception.EmailAlreadyExistsException;
import com.rvk.mtbs.exception.InvalidPasswordException;
import com.rvk.mtbs.exception.PasswordMismatchException;
import com.rvk.mtbs.exception.UserNotFoundException;
import com.rvk.mtbs.mapper.UserMapper;
import com.rvk.mtbs.repository.UserRepository;
import com.rvk.mtbs.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserResponse register(UserRegisterRequest request) {

		if (userRepository.findByEmail(request.email()).isPresent())
			throw new EmailAlreadyExistsException("Email is already registered");

		User user = UserMapper.toEntity(request);
		user.setPassword(encoder.encode(request.password()));
		User savedUser = userRepository.save(user);
		return UserMapper.toResponse(savedUser);
	}

	@Override
	@Transactional
	public UserResponse createStaff(UserCreateRequest request, User creator) {

		if (userRepository.findByEmail(request.email()).isPresent())
			throw new EmailAlreadyExistsException("Email is already registered");

		User user = UserMapper.toEntity(request);

		switch (creator.getRole()) {

		case MANAGER -> {
			if (request.role() == Role.MANAGER)
				throw new AccessDeniedException("Only a MANAGER can create another MANAGER account");
			user.setRole(request.role());
		}
		case ADMIN -> {
			if (request.role() == Role.MANAGER || request.role() == Role.ADMIN)
				throw new AccessDeniedException("Admin can only create OWNER accounts");
			user.setRole(request.role());
		}
		case OWNER -> {
			throw new AccessDeniedException("Owner cannot create staff accounts");
		}
		default -> {
			throw new AccessDeniedException("Access denied");
		}
		}

		user.setPassword(encoder.encode(request.password()));
		User savedUser = userRepository.save(user);
		return UserMapper.toResponse(savedUser);
	}

	@Override
	public List<UserResponse> getAll() {
		return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
	}

	@Override
	public UserResponse getById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		return UserMapper.toResponse(user);
	}

	@Override
	@Transactional
	public UserResponse update(Long id, UserUpdateRequest request) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		Optional<User> existing = userRepository.findByEmail(request.email());

		if (existing.isPresent() && !existing.get().getId().equals(id)) {
			throw new EmailAlreadyExistsException("Email is already registered");
		}

		user.setName(request.name());
		user.setEmail(request.email());
		User savedUser = userRepository.save(user);
		return UserMapper.toResponse(savedUser);
	}

	@Override
	@Transactional
	public void updateUserPassword(Long id, UserUpdatePasswordRequest request) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		if (!encoder.matches(request.oldPassword(), user.getPassword()))
			throw new InvalidPasswordException("Old password is incorrect");

		else if (!request.newPassword().equals(request.confirmNewPassword()))
			throw new PasswordMismatchException("Password mismatch");

		user.setPassword(encoder.encode(request.newPassword()));

		userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
		user.setStatus(UserStatus.INACTIVE);
		userRepository.save(user);
	}
}
