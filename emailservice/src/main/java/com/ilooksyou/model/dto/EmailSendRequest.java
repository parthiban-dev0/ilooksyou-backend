package com.ilooksyou.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record EmailSendRequest(
        @NotNull(message = "Template ID is required")
        String templateId,
        @NotNull(message = "Subject is required")
        String subject,
        @NotEmpty(message = "To Addresses are required")
        List<String> to,
        List<String> bcc,
        List<String> cc,
        String replyTo,
        Map<String, Object> data) {
}
