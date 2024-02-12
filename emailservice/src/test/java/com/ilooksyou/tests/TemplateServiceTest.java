package com.ilooksyou.tests;

import com.ilooksyou.model.Template;
import com.ilooksyou.model.dto.TemplateRequest;
import com.ilooksyou.service.TemplateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TemplateServiceTest {

    @Autowired
    private TemplateService templateService;

    @Test
    public void createTemplateTest(){
        TemplateRequest request = new TemplateRequest("Test-1","<p>Hello world</p>");
        Template template = templateService.create(request);
        assertThat(template).isNotNull();
        assertThat(template.getId()).isNotEmpty();
    }
}
