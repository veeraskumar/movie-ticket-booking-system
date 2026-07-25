package com.rvk.mtbs.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.rvk.mtbs.repository.BookingRepository;
import com.rvk.mtbs.repository.ShowRepository;
import com.rvk.mtbs.repository.TheaterRepository;

import lombok.RequiredArgsConstructor;

@Component("resourceSecurity")
@RequiredArgsConstructor
public class ResourceSecurity {

	private final TheaterRepository theaterRepository;
	private final ShowRepository showRepository;
	private final BookingRepository bookingRepository;

	private Long currentUserId(Authentication authentication) {
		return ((CustomUserDetails) authentication.getPrincipal()).getUser().getId();
	}

	public boolean isTheaterOwner(Long theaterId, Authentication authentication) {
		return theaterRepository.findById(theaterId)
				.map(t -> t.getOwner().getId().equals(currentUserId(authentication))).orElse(false);
	}

	public boolean isShowsTheaterOwner(Long showId, Authentication authentication) {
		return showRepository.findById(showId)
				.map(s -> s.getTheater().getOwner().getId().equals(currentUserId(authentication))).orElse(false);
	}

	public boolean isSelf(Long userId, Authentication authentication) {
		return currentUserId(authentication).equals(userId);
	}

	public boolean isBookingOwner(Long bookingId, Authentication authentication) {
		return bookingRepository.findById(bookingId).map(b -> b.getUser().getId().equals(currentUserId(authentication)))
				.orElse(false);
	}
}