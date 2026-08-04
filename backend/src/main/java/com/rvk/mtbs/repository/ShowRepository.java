package com.rvk.mtbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvk.mtbs.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
	
	List<Show> findByTheaterId(Long theaterId);

}
