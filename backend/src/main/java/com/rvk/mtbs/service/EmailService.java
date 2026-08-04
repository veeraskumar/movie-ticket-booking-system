package com.rvk.mtbs.service;

public interface EmailService {
	void sendBookingConfirmation(String toEmail, String userName, String movieName, String seatNumbers, int totalPrice);

	void sendBookingCancellation(String toEmail, String userName, String movieName);

	void sendPasswordResetCode(String email, int code);

	void sendBookingCancellation(String toEmail, String userName, String movieName, String seatNumbers,
			int refundAmount);
}