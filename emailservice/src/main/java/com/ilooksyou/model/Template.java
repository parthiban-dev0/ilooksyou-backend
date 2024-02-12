package com.ilooksyou.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "templates")
@Getter
@Setter
public class Template {

    @Id
    private String id;
    private String title;
    private String htmlContent;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
