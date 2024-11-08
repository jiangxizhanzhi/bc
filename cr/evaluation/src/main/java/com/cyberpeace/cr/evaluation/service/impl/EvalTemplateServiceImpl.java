package com.cyberpeace.cr.evaluation.service.impl;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 评估模板Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class EvalTemplateServiceImpl extends ServiceImpl<EvalTemplateMapper, EvalTemplate> implements EvalTemplateService {

	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 评估模板Mapper
	 */
	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	/**
	 * 评估任务Service
	 */
	@Autowired
	private EvalTaskService evalTaskService;

	/**
	 * 评估任务Mapper
	 */
	@Autowired
	private EvalTaskMapper evalTaskMapper;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 获取模板
	 *
	 * @param templateId 模板id
	 * @return {@code EvalTemplate }
	 */
	@Override
	public EvalTemplate getTemplate(String templateId) {

		EvalTemplate evalTemplate = getById(templateId);

		// 校验模板是否存在
		if (evalTemplate == null) {
			log.error("Training not exists! templateId = [{}]", templateId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		// 校验用户权限
		if (!Objects.equals(evalTemplate.getCreateUserId(),
				UserInfoContext.getUserContext().getUserId())) {
			log.error("Permission denied! userId = [{}]", UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		return evalTemplate;
	}

	/**
	 * 列表
	 *
	 * @param evalTemplateListQuery EVAL 模板列表查询
	 * @return {@code IPage<EvalTemplate> }
	 */
	@Override
	public IPage<EvalTemplate> list(@Validated EvalTemplateListQuery evalTemplateListQuery) {

		Page page = evalTemplateListQuery.toPageParam();

		// 根据用户获取模板列表
        return evalTemplateMapper.listEvalTemplates(page, evalTemplateListQuery,
                UserInfoContext.getUserContext().getUserId());
	}

	/**
	 * 列出任务
	 *
	 * @param evalTemplate eval 模板
	 * @param isFinished   已完成
	 * @return {@code List<EvalTaskSlimContext> }
	 */
	@Override
	public List<EvalTaskSlimContext> listTask(EvalTemplate evalTemplate, Boolean isFinished) {

		if (isFinished != null && isFinished) {
			return evalTaskMapper.selectSlimTaskListByTemplateIdAndStatus(
					evalTemplate.getResourceId(), EvalTask.Status.FINISHED.getCode());
		}

		List<EvalTaskSlimContext> evalTaskSlimContexts = evalTaskMapper
				.selectSlimTaskListByTemplateIdAndStatus(evalTemplate.getResourceId(), null);

		if (StringUtils.isEmpty(evalTemplate.getCurrentTaskId())) {
			evalTaskSlimContexts.add(new EvalTaskSlimContext());
		}

		return evalTaskSlimContexts;
	}

	/**
	 * 获取图标路径
	 *
	 * @param attachmentId 附件id
	 * @return {@code String }
	 */
	@Override
	public String getLogoPath(String attachmentId) {
		Attachment attachment = attachmentService.getById(attachmentId);
		if (attachment != null) {
			return utilService.handlerFileUrl(attachment.getFilePath());
		}
		return "";
	}

	/**
	 * 批量删除
	 *
	 * @param templateIds 模板id列表
	 * @return {@code Boolean }
	 */
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

	/**
	 * 验证存在任务
	 *
	 * @param templateId 模板id
	 * @param taskStatus 任务状态
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean validateExistTask(String templateId, EvalTask.Status taskStatus) {

		List<String> evalTaskList = evalTaskMapper.selectIdsByTemplateIdAndStatus(templateId,
				taskStatus.getCode());

		return !evalTaskList.isEmpty();

	}

	/**
	 * 验证基础信息
	 *
	 * @param evalTemplateBaseInfoContext eval 模板 base info 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {

		String templateName = evalTemplateBaseInfoContext.getName();
		String templateId = evalTemplateBaseInfoContext.getResourceId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(templateName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTemplateMapper.existByNameAndUserIdAndId(templateName,
				UserInfoContext.getUserContext().getUserId(), templateId)) {
			log.error("Name duplicate! templateName = [{}]", templateName);
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述
		CrCommonValidator.validateLength(evalTemplateBaseInfoContext.getDescription(),
				CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		// 校验LOGO文件是否存在
		if (!attachmentService.isExist(evalTemplateBaseInfoContext.getLogoId())) {
			log.error("The logo file does not exist! logoId = [{}]", evalTemplateBaseInfoContext.getLogoId());
			throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
		}

		// 校验测试靶场的类型是否存在
		if (!crConstantsService.validateType(
				CrConstantsConfig.ConstantsType.EVALUATION_CATEGORY.getCode(),
				evalTemplateBaseInfoContext.getCategoryCode())) {
			log.error("The evaluation type does not exist! categoryCode = [{}]", evalTemplateBaseInfoContext.getCategoryCode());
			throw new BusinessException(CrBusinessErrorCode.EVAL_CATEGORY_NOT_EXIST);
		}

		return true;
	}

	/**
	 * 验证步配置
	 *
	 * @param evalTaskStepConfigList 评估任务步骤配置列表
	 * @return {@code Boolean }
	 */
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
				|| !ListUtil.compare2List(stageList, expectStageList)) {
			log.error("The configuration of the evaluation step is incorrect! expectStageList = [{}]", JsonUtil.toJsonString(stageList));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}

		return true;
	}

}