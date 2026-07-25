package com.rvk.mtbs.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rvk.mtbs.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Override
	public void sendBookingConfirmation(String toEmail, String userName, String movieName, String seatNumbers,
			int totalPrice) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Booking Confirmed - " + movieName);
		message.setText(String.format(
				"Hi %s,%n%nYour booking for \"%s\" is confirmed.%nSeats: %s%nTotal: %d%n%nEnjoy the show!", userName,
				movieName, seatNumbers, totalPrice));
		send(message);
	}

	@Override
	public void sendBookingCancellation(String toEmail, String userName, String movieName) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Booking Cancelled - " + movieName);
		message.setText(String.format("Hi %s,%n%nYour booking for \"%s\" has been cancelled.", userName, movieName));
		send(message);
	}

	private void send(SimpleMailMessage message) {
		try {
			mailSender.send(message);
		} catch (Exception ex) {
			log.error("Failed to send email to {}", message.getTo(), ex);
		}
	}

	@Override
	public void sendPasswordResetCode(String toEmail, int code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Forgot Password - RVK Services");
		message.setText("Your password reset code is: " + code + "\n\nThis code will expire in 15 minutes.");
		send(message);
	}

	@Override
	public void sendBookingCancellation(String toEmail, String userName, String movieName, String seatNumbers,
			int refundAmount) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Booking Cancelled - " + movieName);

		message.setText(String.format("""
				Hi %s,

				Your booking for "%s" has been cancelled successfully.

				Cancelled Seats: %s
				Refund Amount: ₹%d

				If applicable, your refund will be processed shortly.

				Thank you for using RVK Movie Ticket Booking System.
				""", userName, movieName, seatNumbers, refundAmount));

		send(message);
	}

}