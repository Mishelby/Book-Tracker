package org.example.booktracker.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.evenListener.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Slf4j
@Component
public class EmailSenderService {
    final Logger logger = Logger.getLogger(EmailSenderService.class.getName());
    private final JavaMailSender mailSender;
    private final String emailFrom;

    @Autowired
    public EmailSenderService(
            JavaMailSender mailSender,
            @Value("${spring.mail.username}")
            String emailFrom
    ) {
        this.mailSender = mailSender;
        this.emailFrom = emailFrom;
    }

    @EventListener
    @Async
    public void process(EmailEvent event) {
        sendEmail(event);
    }

    protected void sendEmail(EmailEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

            helper.setFrom(emailFrom);
            helper.setTo(event.getEmailTo());
            helper.setSubject("Book Tracker test email");
            helper.setText(event.getMessage());

            mailSender.send(message);
            logger.info(() -> "Email sent successfully to = %s".formatted(event.getEmailTo()));
        } catch (Exception e) {
            logger.warning(() -> "Exception during send email = %s".formatted(e.getMessage()));
        }
    }
}
