package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.BookingRequest;
import com.rvk.mtbs.dto.response.BookingResponse;

public interface BookingService {

	BookingResponse create(BookingRequest request);

	List<BookingResponse> getAll();

	BookingResponse getById(Long id);

	BookingResponse cancel(Long id);
}
