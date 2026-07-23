package com.rvk.mtbs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.request.UserUpdatePasswordRequest;
import com.rvk.mtbs.dto.request.UserUpdateRequest;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.entity.User;
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
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserResponse register(UserRegisterRequest request) {

		if (userRepository.findByEmail(request.email()).isPresent())
			throw new EmailAlreadyExistsException("Email is already registered");

		User user = UserMapper.toEntity(request);
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

		if (!user.getPassword().equals(request.oldPassword()))
			throw new InvalidPasswordException("Invalid password");
		else if (!request.newPassword().equals(request.confirmNewPassword()))
			throw new PasswordMismatchException("Password mismatch");

		user.setPassword(request.newPassword());

		userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		userRepository.delete(user);
	}
}
