package com.rvk.mtbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "booking_seats", uniqueConstraints = @UniqueConstraint(columnNames = { "show_id", "seat_number" }))
public class BookingSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer seatNumber;

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	@ManyToOne
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;
}