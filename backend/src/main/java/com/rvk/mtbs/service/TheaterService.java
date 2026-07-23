package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;

public interface TheaterService {

	TheaterResponse create(TheaterRequest request);

	List<TheaterResponse> getAll();

	TheaterResponse getById(Long id);

	TheaterResponse update(Long id, TheaterRequest request);

	void delete(Long id);
}
