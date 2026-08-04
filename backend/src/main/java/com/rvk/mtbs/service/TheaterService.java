package com.rvk.mtbs.service;

import java.util.List;

import com.rvk.mtbs.dto.request.TheaterRequest;
import com.rvk.mtbs.dto.response.TheaterResponse;
import com.rvk.mtbs.entity.User;
import com.rvk.mtbs.enums.City;

public interface TheaterService {

	TheaterResponse create(TheaterRequest request, User user);

	List<TheaterResponse> getAll();

	List<TheaterResponse> getAllByCity(City city);

	List<TheaterResponse> findByOwnerId(User user);

	TheaterResponse getById(Long id);

	TheaterResponse update(Long id, TheaterRequest request);

	TheaterResponse shutdown(Long id);

}
