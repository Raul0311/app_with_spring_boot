package com.example.demo.adapter.out.persistence;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.application.ports.out.EmailPortOut;

/**
 * The Class EmailAdapterOut.
 */
@Service
public class EmailAdapterOut implements EmailPortOut {

	/** The mail sender. */
	private final JavaMailSender mailSender;

    /**
     * Instantiates a new email adapter out.
     *
     * @param mailSender the mail sender
     */
    public EmailAdapterOut(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send reset password email.
     *
     * @param email the email
     * @param token the token
     */
    public void sendResetPasswordEmail(String email, String token) {
        String resetLink = "http://localhost:8080/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de contraseña");
        message.setText(
                "Has solicitado restablecer tu contraseña.\n\n" +
                "Haz clic aquí para continuar:\n" +
                resetLink + "\n\n" +
                "Si no fuiste tú, ignora este mensaje."
        );

        mailSender.send(message);
    }
}
