package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateListQuery;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateBaseInfoContext;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTemplateServiceImpl extends ServiceImpl<EvalTemplateMapper, EvalTemplate> implements EvalTemplateService {

	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CrConstantsService crConstantsService;

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
			return utilService.handlerFileUrl(attachment.getFilePath());
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

	@Override
	public Boolean validateExistTask(String templateId, EvalTask.Status taskStatus) {

		List<EvalTask> evalTaskList = evalTaskMapper.selectByTemplateIdAndStatus(templateId,
				taskStatus.getCode());

		return !evalTaskList.isEmpty();

	}

	@Override
	public Boolean validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {

		String templateName = evalTemplateBaseInfoContext.getName();
		String templateId = evalTemplateBaseInfoContext.getResourceId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(templateName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTemplateMapper.existByNameAndUserIdAndId(templateName,
				UserInfoContext.getUserContext().getUserId(), templateId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述
		CrCommonValidator.validateLength(evalTemplateBaseInfoContext.getDescription(),
				CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		// 校验LOGO文件是否存在
		if (!attachmentService.isExist(evalTemplateBaseInfoContext.getLogoId())) {
			throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
		}

		// 校验测试靶场的类型是否存在
		if (!crConstantsService.validateType(
				CrConstantsConfig.ConstantsType.EVALUATION_CATEGORY.getCode(),
				evalTemplateBaseInfoContext.getCategoryCode())) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_CATEGORY_NOT_EXIST);
		}

		return true;
	}

	@Override
	public Boolean validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList) {
		List<Integer> stageList = com.google.common.collect.Lists.newArrayList();

		for (EvalTaskStepConfig evalTaskStepConfig : evalTaskStepConfigList) {
			evalTaskStepConfig.validate();
			stageList.add(evalTaskStepConfig.getStage());
		}

		if (!evalTaskStepConfigList.isEmpty()
				&& !ListUtil.compare2List(stageList, ListUtil.listRangeInt(1, stageList.size()))) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}

		return true;
	}

}