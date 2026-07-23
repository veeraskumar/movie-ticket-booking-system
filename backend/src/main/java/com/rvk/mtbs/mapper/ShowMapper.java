package com.rvk.mtbs.mapper;

import java.time.LocalDateTime;

import com.rvk.mtbs.dto.request.ShowRequest;
import com.rvk.mtbs.dto.response.ShowResponse;
import com.rvk.mtbs.entity.Show;
import com.rvk.mtbs.entity.Theater;

public final class ShowMapper {

	private ShowMapper() {
	}

	public static ShowResponse toResponse(Show show) {
		return new ShowResponse(show.getId(), show.getMovieName(), show.getRoomNumber(), show.getStartTime(),
				show.getDurationMinutes(), show.getTotalSeats(), show.getEconomySeatTo(), show.getEconomySeatPrice(),
				show.getPremiumSeatTo(), show.getPremiumSeatPrice(), show.getReclinerSeatTo(),
				show.getReclinerSeatPrice(), show.getTheater().getName());
	}

	public static Show toEntity(ShowRequest request, Theater theater) {
		Show show = new Show();

		show.setMovieName(request.movieName());
		show.setRoomNumber(request.roomNumber());
		show.setStartTime(request.startTime());
		show.setDurationMinutes(request.durationMinutes());
		show.setTotalSeats(request.totalSeats());
		show.setEconomySeatTo(request.economySeatTo());
		show.setEconomySeatPrice(request.economySeatPrice());
		show.setPremiumSeatTo(request.premiumSeatTo());
		show.setPremiumSeatPrice(request.premiumSeatPrice());
		show.setReclinerSeatTo(request.reclinerSeatTo());
		show.setReclinerSeatPrice(request.reclinerSeatPrice());
		show.setCreatedAt(LocalDateTime.now());
		show.setTheater(theater);

		return show;
	}
}
