package com.ilooksyou.service;

import com.ilooksyou.model.Template;
import com.ilooksyou.model.dto.TemplateRequest;

public interface TemplateService {

    public Template create(TemplateRequest request);
}
