/**
 * 这段代码定义了一个名为 `EvalTemplateServiceImpl` 的类，该类实现了 `EvalTemplateService` 接口。这个类主要用于处理与评估模板相关的业务逻辑。以下是对其主要功能和结构的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**: `EvalTemplateServiceImpl` 
 * - **继承**: 继承了 `ServiceImpl<EvalTemplateMapper, EvalTemplate>`，这表明它使用了 MyBatis-Plus 框架来操作数据库。 
 * - **注解**: 
 * - `@Service`: 表明这是一个 Spring 管理的服务层组件。 
 * - `@Slf4j`: 使用 Lombok 自动生成日志对象。 
 * ### 成员变量 
 * - **依赖注入**: 
 * - `attachmentService`: 处理附件相关操作。 
 * - `utilService`: 提供一些工具方法。 
 * - `crConstantsService`: 提供常量服务。 
 * - `evalTemplateMapper`: 处理评估模板的数据库操作。 
 * - `evalTaskService`: 处理评估任务的相关操作。 
 * - `evalTaskMapper`: 处理评估任务的数据库操作。 
 * - `remoteTopoService`: 远程拓扑服务。 
 * ### 方法 
 * #### 1. `getTemplate(String templateId)` 
 * - **功能**: 根据模板ID获取评估模板，并校验模板是否存在及当前用户是否有权限访问该模板。 
 * - **返回值**: `EvalTemplate` 对象或抛出异常。 
 * #### 2. `list(EvalTemplateListQuery evalTemplateListQuery)` 
 * - **功能**: 根据查询条件获取评估模板列表。 
 * - **参数**: `EvalTemplateListQuery` 查询条件对象。 
 * - **返回值**: 分页后的 `IPage<EvalTemplate>` 对象。 
 * #### 3. `listTask(EvalTemplate evalTemplate, Boolean isFinished)` 
 * - **功能**: 获取指定模板下的任务列表，可按完成状态过滤。 
 * - **参数**: `EvalTemplate` 对象和一个布尔值 `isFinished` 表示是否只返回已完成的任务。 
 * - **返回值**: `List<EvalTaskSlimContext>` 对象。 
 * #### 4. `getLogoPath(String attachmentId)` 
 * - **功能**: 获取附件（通常是模板的logo）的路径。 
 * - **参数**: 附件ID。 
 * - **返回值**: 附件的URL路径。 
 * #### 5. `batchDelete(List<String> templateIds)` 
 * - **功能**: 批量删除评估模板及其关联的任务和拓扑。 
 * - **参数**: 模板ID列表。 
 * - **返回值**: 是否删除成功。 
 * #### 6. `validateExistTask(String templateId, EvalTask.Status taskStatus)` 
 * - **功能**: 检查指定模板下是否存在指定状态的任务。 
 * - **参数**: 模板ID和任务状态。 
 * - **返回值**: 布尔值表示是否存在指定状态的任务。 
 * #### 7. `validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext)` 
 * - **功能**: 验证模板的基本信息，包括名称、描述、Logo等。 
 * - **参数**: `EvalTemplateBaseInfoContext` 包含模板的基本信息。 
 * - **返回值**: 布尔值表示验证是否通过。 
 * #### 8. `validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList)` 
 * - **功能**: 验证评估任务的步骤配置。 
 * - **参数**: `EvalTaskStepConfig` 列表。 
 * - **返回值**: 布尔值表示验证是否通过。 
 * ### 总结 
 * 该类主要负责处理评估模板的CRUD操作以及相关的业务逻辑验证。通过依赖注入的方式引入了多个服务和映射器，从而能够方便地进行数据库操作和调用其他服务。整体设计遵循了良好的分层架构原则，确保了代码的可维护性和可扩展性。 
**/
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