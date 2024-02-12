package com.ilooksyou.model.dto;

import jakarta.validation.constraints.NotNull;

public record TemplateRequest(
        @NotNull(message = "Title is required")
        String title,
        @NotNull(message = "Html content is required")
        String htmlContent) {
}
