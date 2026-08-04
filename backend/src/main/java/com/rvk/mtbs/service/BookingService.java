package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;
import com.rvk.mtbs.entity.User;

public interface BookingService {

	BookingResponse create(BookingRequest request, User user);

	List<BookingResponse> getAll();

	BookingResponse getById(Long id);

	BookingResponse cancel(Long id, List<Integer> seats);

	List<BookingResponse> getAllById(Long userId);
}
