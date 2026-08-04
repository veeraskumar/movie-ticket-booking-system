package com.rvk.mtbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvk.mtbs.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	Optional<PasswordResetToken> findByUserEmailAndCodeAndUsedFalse(String email, Integer code);
}