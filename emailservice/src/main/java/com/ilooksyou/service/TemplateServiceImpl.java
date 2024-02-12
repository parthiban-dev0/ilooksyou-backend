package com.ilooksyou.service;

import com.ilooksyou.model.Template;
import com.ilooksyou.model.dto.TemplateRequest;
import com.ilooksyou.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public Template create(TemplateRequest request) {
        Template template = new Template();
        template.setTitle(request.title());
        template.setHtmlContent(request.htmlContent());
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        Template savedTemplate = this.templateRepository.save(template);
        return savedTemplate;
    }

}
