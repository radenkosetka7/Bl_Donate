package com.example.bldonate.services.impl;

import com.example.bldonate.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailImpl implements EmailService {


    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    public EmailImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendSimpleMailApproved(String recipient) throws Exception {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            String textMaila="Poštovani \n\nVaš zahtjev za nalog je odoboren.\nDobro došli u BlDonate tim!\n\nSrdačan pozdrav,\nBlDonate admin tim";
            mailMessage.setText(textMaila);
            mailMessage.setSubject("Potvrda o registaciji");
            javaMailSender.send(mailMessage);
    }

    @Override
    @Async
    public void sendSimpleMailDeleted(String recipient) throws Exception {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        String textMaila="Poštovani \n\nVaš nalog je obrisan.\nAko smatrate da se radi o grešci, obratite se administratorskom timu putem e-maila!\n\nSrdačan pozdrav,\nBlDonate admin tim";
        mailMessage.setText(textMaila);
        mailMessage.setSubject("Brisanje naloga");
        javaMailSender.send(mailMessage);

    }


}
