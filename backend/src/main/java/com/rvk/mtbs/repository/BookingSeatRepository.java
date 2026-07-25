package com.rvk.mtbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rvk.mtbs.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

	@Query("SELECT bs.seatNumber FROM BookingSeat bs "
			+ "WHERE bs.show.id = :showId AND bs.booking.status = 'CONFIRMED'")
	List<Integer> findBookedSeatNumbers(@Param("showId") Long showId);
}
