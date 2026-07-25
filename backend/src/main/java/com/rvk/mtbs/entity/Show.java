package com.rvk.mtbs.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "shows")
public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String movieName;

	@Column(nullable = false)
	private Integer roomNumber;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private Integer durationMinutes;

	@Column(nullable = false)
	private Integer totalSeats;

	@Column(nullable = false)
	private Integer economySeatTo;

	@Column(nullable = false)
	private Integer economySeatPrice;

	@Column(nullable = false)
	private Integer premiumSeatTo;

	@Column(nullable = false)
	private Integer premiumSeatPrice;

	@Column(nullable = false)
	private Integer reclinerSeatTo;

	@Column(nullable = false)
	private Integer reclinerSeatPrice;

	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "theater_id", nullable = false)
	private Theater theater;

}
