package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.UserRegisterRequest;
import com.rvk.mtbs.dto.request.UserUpdatePasswordRequest;
import com.rvk.mtbs.dto.request.UserUpdateRequest;
import com.rvk.mtbs.dto.response.UserResponse;

public interface UserService {

	UserResponse register(UserRegisterRequest request);

	List<UserResponse> getAll();

	UserResponse getById(Long id);

	UserResponse update(Long id, UserUpdateRequest request);

	void updateUserPassword(Long id, UserUpdatePasswordRequest request);

	void delete(Long id);
}
