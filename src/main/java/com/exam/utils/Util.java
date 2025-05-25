package com.exam.utils;

import java.util.Properties;


//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class Util {

//	@Value("${spring.mail.username}")
//	private String username;;
//	
//	@Value("${spring.mail.password}")
//	private String password;
//	
//	public void sendEmail(String to, String subject, String body) {
//        // Setup mail sender
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//
//        // SMTP properties
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.starttls.required", "true");
//        props.put("mail.debug", "false");
//
//        // Create and send message
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(mailSender.getUsername());
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body,true);
//        
//        mailSender.send(message);
//    }
	@Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public void sendEmail(String to, String subject, String htmlBody) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);  // <-- THIS enables HTML

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

