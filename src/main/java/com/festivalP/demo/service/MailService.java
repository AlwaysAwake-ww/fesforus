//package com.festivalP.demo.service;
//
//import com.festivalP.demo.domain.Contact;
//import lombok.AllArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class MailService {
//    private JavaMailSender mailSender;
//    private static final String TO_ADDRESS = "kji758002@gmail.com";
//
//    public void mailSend(Contact contact) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(contact.getcontactEmail());
//        message.setTo(TO_ADDRESS);
//        message.setText(contact.getcontactText());
//
//        mailSender.send(message);
//
//    }
//}
