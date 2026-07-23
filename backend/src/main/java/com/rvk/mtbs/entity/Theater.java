package com.rvk.mtbs.entity;

import java.time.LocalDateTime;

import com.rvk.mtbs.enums.City;
import com.rvk.mtbs.enums.TheaterStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "theaters")
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer noOfRooms;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private City city;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String googleMapUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TheaterStatus status;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;
}
