package com.ilooksyou.message.dto;

import com.ilooksyou.message.GeneralMessage;

public record GeneralMessageRequest(String[] types, GeneralMessage message) {
}
