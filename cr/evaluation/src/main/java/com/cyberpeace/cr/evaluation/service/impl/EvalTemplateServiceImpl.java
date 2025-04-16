package com.cyberpeace.cr.evaluation.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateBaseInfoContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTemplateServiceImpl extends ServiceImpl<EvalTemplateMapper, EvalTemplate> implements EvalTemplateService {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CrConstantsService crConstantsService;

	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Override
	public EvalTemplate getTemplate(String templateId) {

		EvalTemplate evalTemplate = getById(templateId);

		// 校验模板是否存在
		if (evalTemplate == null) {
			log.error("Training not exists! templateId = [{}]", templateId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		// 校验用户权限
		String currentId = UserInfoContext.getUserContext().getUserId();
		if (!evalTemplate.isFree2Operate() && !evalTemplate.getCreateUserId().equals(currentId)
				&& !evalTemplate.getOperationAuthIds().contains(currentId)) {
			log.error("Permission denied! userId = [{}]",
					UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		return evalTemplate;
	}

	@Override
	public IPage<EvalTemplate> list(@Validated EvalTemplateListQuery evalTemplateListQuery) {

		Page<EvalTemplate> page = evalTemplateListQuery.toPageParam();

		// 根据用户获取模板列表
        return evalTemplateMapper.listEvalTemplates(page, evalTemplateListQuery,
                UserInfoContext.getUserContext().getUserId());
	}

	@Override
	public List<EvalTaskSlimContext> listTask(EvalTemplate evalTemplate, Boolean isFinished) {
		List<EvalTaskSlimContext> evalTaskSlimContexts = null;
		if (isFinished != null && isFinished) {
			evalTaskSlimContexts = evalTaskMapper.selectSlimTaskListByTemplateIdAndStatus(
					evalTemplate.getResourceId(), EvalTask.Status.FINISHED.getCode());
			return evalTaskSlimContexts;
		} else {
			evalTaskSlimContexts = evalTaskMapper
					.selectSlimTaskListByTemplateIdAndStatus(evalTemplate.getResourceId(), null);
			if (StringUtils.isEmpty(evalTemplate.getCurrentTaskId())) {
				evalTaskSlimContexts.add(new EvalTaskSlimContext());
			}
		}
		for(EvalTaskSlimContext task : evalTaskSlimContexts){
			if(task.getStartTime() == null){
				continue;
			}
			task.setStartSearchTime(task.getStartTime());
			// 防止开始时间和结束时间是同一天
			try {
				task.setEndSearchTime(task.getEndTime().plusDays(1));
			} catch (Exception e) {
				// 当前时间
				task.setEndSearchTime(LocalDateTime.now().plusDays(1));
			}
		}
		return evalTaskSlimContexts;
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

		for (String templateId : templateIds) {
			// 删除task及相关场景
			List<String> evalTaskList = evalTaskMapper.selectIdsByTemplateIdAndStatus(templateId,
					null);
			for (String taskId : evalTaskList) {
				evalTaskService.deleteTask(evalTaskService.getEvalTaskOpsContext(taskId, true));
			}

			EvalTemplate evalTemplate = evalTemplateMapper.selectById(templateId);
			if (StringUtils.isNotBlank(evalTemplate.getTopoId())) {
				try {
					remoteTopoService.deleteById(evalTemplate.getTopoId());
				} catch (Exception e) {
					log.error(
							"Evaluate template delete topo failed! template_id=[{}], topo_id=[{}], catch error:",
							evalTemplate.getResourceId(), evalTemplate.getTopoId(), e);
				}
			}

			evalTemplateMapper.deleteById(templateId);
		}

		return true;

	}

	@Override
	public Boolean validateExistTask(String templateId, EvalTask.Status taskStatus) {

		List<String> evalTaskList = evalTaskMapper.selectIdsByTemplateIdAndStatus(templateId,
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
		if (Boolean.TRUE.equals(evalTemplateMapper.existByNameAndUserIdAndId(templateName,
				UserInfoContext.getUserContext().getUserId(), templateId))) {
			log.error("Name duplicate! templateName = [{}]", templateName);
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述
		CrCommonValidator.validateLength(evalTemplateBaseInfoContext.getDescription(),
				CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		// 校验LOGO文件是否存在
		if (Boolean.FALSE
				.equals(attachmentService.isExist(evalTemplateBaseInfoContext.getLogoId()))) {
			log.error("The logo file does not exist! logoId = [{}]", evalTemplateBaseInfoContext.getLogoId());
			throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
		}

		// 校验测试靶场的类型是否存在
		if (Boolean.FALSE.equals(crConstantsService.validateType(
				CrConstantsConfig.ConstantsType.EVALUATION_CATEGORY.getCode(),
				evalTemplateBaseInfoContext.getCategoryCode()))) {
			log.error("The evaluation type does not exist! categoryCode = [{}]", evalTemplateBaseInfoContext.getCategoryCode());
			throw new BusinessException(CrBusinessErrorCode.EVAL_CATEGORY_NOT_EXIST);
		}

		return true;
	}

	@Override
	public Boolean validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList) {

		if (evalTaskStepConfigList.isEmpty()) {
			return true;
		}

		List<Integer> stageList = Lists.newArrayList();
		List<String> titleList = Lists.newArrayList();

		// 校验步骤配置是否为空
		for (EvalTaskStepConfig evalTaskStepConfig : evalTaskStepConfigList) {
			evalTaskStepConfig.validate();
			stageList.add(evalTaskStepConfig.getStage());
			titleList.add(evalTaskStepConfig.getTitle());
		}

		// 校验步骤标题是否重复
		if (new HashSet<>(titleList).size() != titleList.size()) {
			log.error("Title duplicate! titleList = [{}]", StringUtils.join(titleList, ","));
			throw new BusinessException(CrBusinessErrorCode.TITLE_DUPLICATE);
		}

		// 校验步骤配置的stage
		List<Integer> expectStageList = ListUtil.listRangeInt(1, stageList.size());
		if (new HashSet<>(stageList).size() != expectStageList.size()
				|| Boolean.TRUE.equals(!ListUtil.compare2List(stageList, expectStageList))) {
			log.error("The configuration of the evaluation step is incorrect! expectStageList = [{}]", JsonUtil.toJsonString(stageList));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}

		return true;
	}

}