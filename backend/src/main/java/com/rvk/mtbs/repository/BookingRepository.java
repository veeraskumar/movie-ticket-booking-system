package com.rvk.mtbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvk.mtbs.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
