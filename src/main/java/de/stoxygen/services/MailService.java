package de.stoxygen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String subject, String message) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("hack3d.dev@googlemail.com");
        mail.setFrom("status.stoxygen@gmail.com");
        mail.setSubject(subject);
        mail.setText(message);
        javaMailSender.send(mail);
    }
}
