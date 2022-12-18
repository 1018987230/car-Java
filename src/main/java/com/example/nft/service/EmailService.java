package com.example.nft.service;

import javax.mail.MessagingException;

public interface EmailService {
    String sendCode(String to) throws MessagingException;
}
