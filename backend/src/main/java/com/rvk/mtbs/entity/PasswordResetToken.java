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
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer code;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	@Column(nullable = false)
	private boolean used = false;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}