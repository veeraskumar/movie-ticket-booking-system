package com.rvk.mtbs.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.rvk.mtbs.enums.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "bookings")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer totalPrice;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private BookingStatus status;

	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "show_id", nullable = false)
	private Show show;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BookingSeat> seats = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}
}