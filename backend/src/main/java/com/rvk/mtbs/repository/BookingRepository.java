package com.rvk.mtbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvk.mtbs.entity.Booking;
import com.rvk.mtbs.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByShowIdAndStatus(Long showId, BookingStatus status);

	List<Booking> findAllByUserId(Long userId);


}
