package com.ilooksyou.service;

import com.ilooksyou.message.GeneralMessage;

public interface SenderService<T> {
    void sendMessage(String topic, T message);
}
