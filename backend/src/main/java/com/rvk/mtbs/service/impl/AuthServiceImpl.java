package com.rvk.mtbs.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rvk.mtbs.dto.request.LoginRequest;
import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.response.LoginResponse;
import com.rvk.mtbs.dto.response.UserResponse;
import com.rvk.mtbs.entity.PasswordResetToken;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.exception.EmailAlreadyExistsException;
import com.rvk.mtbs.exception.InvalidResetCodeException;
import com.rvk.mtbs.exception.PasswordMismatchException;
import com.rvk.mtbs.exception.UserNotFoundException;
import com.rvk.mtbs.mapper.UserMapper;
import com.rvk.mtbs.repository.PasswordResetTokenRepository;
import com.rvk.mtbs.repository.UserRepository;
import com.rvk.mtbs.security.JwtService;
import com.rvk.mtbs.service.AuthService;
import com.rvk.mtbs.service.EmailService;
import com.rvk.mtbs.util.GenerateResetCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public LoginResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return new LoginResponse(jwtService.generateToken(userDetails));
	}

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
	public void forgotPassword(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

		int code = GenerateResetCode.generateResetCode();

		PasswordResetToken token = new PasswordResetToken();
		token.setCode(code);
		token.setUser(user);
		token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
		token.setUsed(false);
		passwordResetTokenRepository.save(token);

		emailService.sendPasswordResetCode(email, code);
	}

	@Override
	@Transactional
	public void confirmPassword(String email, int code, String newPassword, String confirmPassword) {

		if (!newPassword.equals(confirmPassword)) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		PasswordResetToken token = passwordResetTokenRepository.findByUserEmailAndCodeAndUsedFalse(email, code)
				.orElseThrow(() -> new InvalidResetCodeException("Invalid or already-used code"));

		if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new InvalidResetCodeException("Code has expired");
		}

		User user = token.getUser();
		user.setPassword(encoder.encode(newPassword));
		userRepository.save(user);

		token.setUsed(true);
		passwordResetTokenRepository.save(token);
	}

}
