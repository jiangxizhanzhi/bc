package com.cyberpeace.cr.singletrain.service.impl;

import com.cyberpeace.cr.singletrain.mapper.TemplateMapper;
import com.cyberpeace.cr.singletrain.model.template.Template;
import com.cyberpeace.cr.singletrain.service.TemplateService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {}
