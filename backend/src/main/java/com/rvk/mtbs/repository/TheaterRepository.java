package com.rvk.mtbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvk.mtbs.entity.Theater;
import com.rvk.mtbs.enums.City;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

	List<Theater> findAllByCity(City city);

	List<Theater> findTheaterByOwner(Long ownerId);
}
