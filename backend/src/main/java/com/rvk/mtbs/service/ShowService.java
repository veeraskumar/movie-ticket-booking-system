package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.ShowRequest;
import com.rvk.mtbs.dto.response.ShowResponse;

public interface ShowService {

	ShowResponse create(ShowRequest request);

	List<ShowResponse> getAll();
	

	ShowResponse getById(Long id);

	ShowResponse update(Long id, ShowRequest request);

	void delete(Long id);
	
	List<ShowResponse> findByTheater(Long theaterId);

	List<Integer> getConfirmedSeats(Long showId);
}
