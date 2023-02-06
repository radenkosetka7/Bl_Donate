package com.example.bldonate.services.impl;

import com.example.bldonate.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

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
            String textMaila="Poštovani, \n\nVaš zahtjev za nalog je odobren.\nDobro došli u BlDonate tim!\n\nSrdačan pozdrav,\nBlDonate admin tim";
            mailMessage.setText(textMaila);
            mailMessage.setSubject("Potvrda o registraciji");
            javaMailSender.send(mailMessage);
    }

    @Override
    @Async
    public void sendSimpleMailDeleted(String recipient) throws Exception {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        String textMaila="Poštovani, \n\nVaš nalog je obrisan.\nAko smatrate da se radi o grešci, obratite se administratorskom timu putem e-maila!\n\nSrdačan pozdrav,\nBlDonate admin tim";
        mailMessage.setText(textMaila);
        mailMessage.setSubject("Brisanje naloga");
        javaMailSender.send(mailMessage);

    }
    @Override
    @Async
    public void sendSimpleMailNotApproved(String recipient) throws Exception {

        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        String textMaila="Poštovani, \n\nVaš zahtjev za nalog nije odobren.\nAko smatrate da se radi o grešci, obratite se administratorskom timu putem e-maila!\n\nSrdačan pozdrav,\nBlDonate admin tim";
        mailMessage.setText(textMaila);
        mailMessage.setSubject("Odbijen zahtjev");
        javaMailSender.send(mailMessage);

    }

    @Override
    public void sendMailResetPassword(String recipient, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipient);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
