package com.rvk.mtbs.service;

import com.rvk.mtbs.dto.request.LoginRequest;
import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.response.LoginResponse;
import com.rvk.mtbs.dto.response.UserResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);

	UserResponse register(UserRegisterRequest request);

	void forgotPassword(String email);

	void confirmPassword(String email, int code, String newPassword, String confirmPassword);
}
