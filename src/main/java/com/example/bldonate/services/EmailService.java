package com.example.bldonate.services;

public interface EmailService {

    void sendSimpleMailApproved(String recipient) throws Exception;

    void sendSimpleMailDeleted(String recipient) throws Exception;

}
