package com.example.bldonate.services;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleMailApproved(String recipient) throws Exception;

    void sendSimpleMailDeleted(String recipient) throws Exception;
     void sendSimpleMailNotApproved(String recipient) throws Exception;

     void sendMailResetPassword(String recipient,String link) throws MessagingException, UnsupportedEncodingException;

}
