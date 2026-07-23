package com.rvk.mtbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rvk.mtbs.dto.request.ShowRequest;
import com.rvk.mtbs.dto.response.ShowResponse;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.Theater;
import com.rvk.mtbs.exception.InvalidSeatConfigurationException;
import com.rvk.mtbs.exception.ShowNotFoundException;
import com.rvk.mtbs.exception.TheaterNotFoundException;
import com.rvk.mtbs.mapper.ShowMapper;
import com.rvk.mtbs.repository.ShowRepository;
import com.rvk.mtbs.repository.TheaterRepository;
import com.rvk.mtbs.service.ShowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowServiceImpl implements ShowService {

	private final ShowRepository showRepository;
	private final TheaterRepository theaterRepository;

	@Override
	@Transactional
	public ShowResponse create(ShowRequest request) {
		Theater theater = theaterRepository.findById(request.theaterId())
				.orElseThrow(() -> new TheaterNotFoundException("Theater not found"));
		Show show = ShowMapper.toEntity(request, theater);

		if (request.economySeatTo() >= request.premiumSeatTo()) {
			throw new InvalidSeatConfigurationException("Economy seats must end before Premium seats.");
		}

		if (request.premiumSeatTo() >= request.reclinerSeatTo()) {
			throw new InvalidSeatConfigurationException("Premium seats must end before Recliner seats.");
		}

		if (!request.reclinerSeatTo().equals(request.totalSeats())) {
			throw new InvalidSeatConfigurationException("Recliner seat end must equal total seats.");
		}

		Show savedShow = showRepository.save(show);
		return ShowMapper.toResponse(savedShow);
	}

	@Override
	public List<ShowResponse> getAll() {
		return showRepository.findAll().stream().map(ShowMapper::toResponse).toList();
	}

	@Override
	public ShowResponse getById(Long id) {
		Show show = showRepository.findById(id).orElseThrow(() -> new ShowNotFoundException("Show not Found"));
		return ShowMapper.toResponse(show);
	}

	@Override
	@Transactional
	public ShowResponse update(Long id, ShowRequest request) {
		Show show = showRepository.findById(id).orElseThrow(() -> new ShowNotFoundException("Show not Found"));
		Theater theater = theaterRepository.findById(request.theaterId())
				.orElseThrow(() -> new TheaterNotFoundException("Theater not found"));

		if (request.economySeatTo() >= request.premiumSeatTo()) {
			throw new InvalidSeatConfigurationException("Economy seats must end before Premium seats.");
		}

		if (request.premiumSeatTo() >= request.reclinerSeatTo()) {
			throw new InvalidSeatConfigurationException("Premium seats must end before Recliner seats.");
		}

		if (!request.reclinerSeatTo().equals(request.totalSeats())) {
			throw new InvalidSeatConfigurationException("Recliner seat end must equal total seats.");
		}

		show.setTheater(theater);
		show.setMovieName(request.movieName());
		show.setDurationMinutes(request.durationMinutes());
		show.setEconomySeatPrice(request.economySeatPrice());
		show.setEconomySeatTo(request.economySeatTo());
		show.setPremiumSeatPrice(request.premiumSeatPrice());
		show.setPremiumSeatTo(request.premiumSeatTo());
		show.setReclinerSeatPrice(request.reclinerSeatPrice());
		show.setReclinerSeatTo(request.reclinerSeatTo());
		show.setRoomNumber(request.roomNumber());
		show.setStartTime(request.startTime());
		show.setTotalSeats(request.totalSeats());

		return ShowMapper.toResponse(show);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Show show = showRepository.findById(id).orElseThrow(() -> new ShowNotFoundException("Show not Found"));
		showRepository.delete(show);
	}

}
