package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateListQuery;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTemplateServiceImpl extends ServiceImpl<EvalTemplateMapper, EvalTemplate> implements EvalTemplateService {

	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Override
	public IPage<EvalTemplate> list(EvalTemplateListQuery evalTemplateListQuery) {

		Page page = evalTemplateListQuery.toPageParam();

		IPage<EvalTemplate> data = evalTemplateMapper.listEvalTemplates(page,
				evalTemplateListQuery);

		return data;
	}

	@Override
	public String getLogoPath(String attachmentId) {
		Attachment attachment = attachmentService.getById(attachmentId);
		if (attachment != null) {
			return attachment.getFilePath();
		}
		return "";
	}

}