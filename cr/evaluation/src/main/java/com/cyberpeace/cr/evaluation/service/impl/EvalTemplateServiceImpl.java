package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
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

	@Override
	public Boolean batchDelete(List<String> templateIds) {

		List<EvalTemplate> evalTemplateList = listTemplate(templateIds);

		// 删除task及相关场景 todo

		evalTemplateMapper.deleteBatchIds(templateIds);

		return true;

	}

	@Override
	public EvalTemplate getTemplate(String templateId) {
		EvalTemplate evalTemplate = getById(templateId);

		if (evalTemplate == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		} else if (!Objects.equals(evalTemplate.getCreateUserId(),
				UserInfoContext.getUserContext().getUserId())) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		} else {
			return evalTemplate;
		}
	}

	private List<EvalTemplate> listTemplate(List<String> templateIds) {
		List<EvalTemplate> evalTemplateList = Lists.newArrayList();
		for (String templateId : templateIds) {
			evalTemplateList.add(getTemplate(templateId));
		}
		return evalTemplateList;
	}

}