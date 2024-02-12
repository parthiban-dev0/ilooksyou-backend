package com.ilooksyou.service;

import com.ilooksyou.model.dto.EmailSendRequest;

public interface EmailSender {

    void sendMail();
    void sendMail(EmailSendRequest request);
}
