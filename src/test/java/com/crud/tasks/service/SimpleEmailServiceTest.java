package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;


    @Test
    public void shouldSendEmail() {
        Mail mail = new Mail("test@test.com", "Test", "Test Message", Optional.of("test@test.com"));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mail.getToCc().ifPresent(cc -> mailMessage.setCc(cc.toString()));


        simpleEmailService.send(mail);


        verify(javaMailSender, times(1)).send(mailMessage);
    }
    @Test
    public void shouldSendEmailWithoutCc() {
        Mail mail = new Mail("test@test.com", "Test", "Test Message", Optional.empty());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mail.getToCc().ifPresent(cc -> mailMessage.setCc(cc.toString()));

        simpleEmailService.send(mail);

        verify(javaMailSender, times(1)).send(mailMessage);
    }
}
