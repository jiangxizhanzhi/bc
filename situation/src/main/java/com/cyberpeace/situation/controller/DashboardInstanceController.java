package com.cyberpeace.situation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.StatusOrderContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.dashboard.PublicStatus;
import com.cyberpeace.situation.constant.dashboard.ReplayMode;
import com.cyberpeace.situation.constant.dashboard.SituationInstanceStatus;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.*;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.model.IntType;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.context.DynamicParameterContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 态势大屏-实例
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "态势大屏-实例")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/publish")
public class DashboardInstanceController {

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@Autowired
	private DashboardTemplateRefService dashboardTemplateRefService;

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	@Autowired
	private SituationCategoryMapper situationCategoryMapper;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private DashboardTemplateRefMapper dashboardTemplateRefMapper;

	@Autowired
	private DashboardTemplateMapper dashboardTemplateMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 大屏实例-列表查询
	 *
	 * @param type 大屏类型 1-综合态势 2-任务态势
	 * @return {@code BaseRsp<List<SituationCategoryRes>>}
	 */
	@ApiOperation("实例类型-列表查询")
	@GetMapping("/category/list")
	public BaseRsp<List<SituationCategoryRes>> getSituationPublishInstanceCategory(
			@ApiParam(name = "type", value = "大屏类型 1-综合态势 2-任务态势") @RequestParam(value = "type",
					required = false) Integer type) {

		// 1、获取所有大屏类型
		List<SituationCategory> situationCategories = situationCategoryMapper
				.selectList(new LambdaQueryWrapper<>());

		// 2、获取所有定制大屏列表
		List<String> categories = dashboardTemplateMapper.selectCustomTypes();

		// 3、根据大屏类型和定制化模板囊括类型进行数据过滤
		List<SituationCategoryRes> res = situationCategories.stream()
				.filter(situationCategory -> checkCategoryType(situationCategory, type))
				.map(situationCategory -> convertCategory(situationCategory, categories))
				.collect(Collectors.toList());
		return new BaseRsp<>(res);
	}

	/**
	 * 校验大屏类型
	 *
	 * @param situationCategory 态势类型
	 * @param type              大屏类型
	 * @return {@code Boolean}
	 */
	private Boolean checkCategoryType(SituationCategory situationCategory, Integer type) {
		if (type == null) {
			return true;
		} else if (type.equals(2)) {
			return StringUtils.isNotEmpty(situationCategory.getTaskType())
					&& !situationCategory.getTaskType().contains("99");
		} else if (type.equals(1)) {
			return StringUtils.isEmpty(situationCategory.getTaskType());
		}
		return true;
	}

	/**
	 * 态势类型设置是否包含定制化模板属性
	 *
	 * @param situationCategory 态势类型
	 * @param categories        包含定制化模板的类型列表
	 * @return {@code SituationCategoryRes}
	 */
	private SituationCategoryRes convertCategory(SituationCategory situationCategory,
			List<String> categories) {
		SituationCategoryRes situationCategoryRes = BeanUtils.copy(situationCategory,
				SituationCategoryRes.class);
		situationCategoryRes.setTemplate(categories.contains(situationCategory.getResourceId()));
		return situationCategoryRes;
	}

	/**
	 * 实例状态-列表查询
	 *
	 * @return {@code BaseRsp<List<SituationInstanceStatusRes>>}
	 */
	@ApiOperation("实例状态-列表查询")
	@GetMapping("/status/list")
	public BaseRsp<List<SituationInstanceStatusRes>> getSituationPublishInstanceStatus() {

		// 获取所有实例状态
		List<SituationInstanceStatusRes> res = new ArrayList<>();
		for (SituationInstanceStatus status : SituationInstanceStatus.values()) {
			SituationInstanceStatusRes copy = BeanUtils.copy(status,
					SituationInstanceStatusRes.class);
			res.add(copy);
		}
		return new BaseRsp<>(res);
	}

	/**
	 * 重演实例类型-列表查询
	 *
	 * @return {@code BaseRsp<List<IntType>>}
	 */
	@ApiOperation("重演实例类型-列表查询")
	@GetMapping("/replay/code/")
	public BaseRsp<List<IntType>> getReplayMode() {
		return new BaseRsp<>(ReplayMode.getList());
	}

	/**
	 * 重演实例-新增
	 *
	 * @param replayInstanceReq 重演实例请求
	 * @return {@code BaseRsp<SituationPublishInstance>}
	 */
	@ApiOperation("重演实例-新增")
	@PostMapping("/replay/")
	public BaseRsp<SituationPublishInstance> addReplayInstance(
			@RequestBody @Validated(Create.class) ReplayInstanceReq replayInstanceReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(replayInstanceReq.getName(),
				replayInstanceReq.getDescription());

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.addReplayInstance(replayInstanceReq);
		return new BaseRsp<>(situationPublishInstance);
	}

	/**
	 * 重演实例-修改
	 *
	 * @param replayInstanceReq 重演实例请求
	 * @return {@code BaseRsp<SituationPublishInstance>}
	 */
	@ApiOperation("重演实例-修改")
	@PatchMapping("/replay/")
	public BaseRsp<SituationPublishInstance> updateReplayInstance(
			@RequestBody @Validated(Update.class) ReplayInstanceReq replayInstanceReq) {
		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.updateReplayInstance(replayInstanceReq);
		return new BaseRsp<>(situationPublishInstance);
	}

	/**
	 * 实例-新增
	 *
	 * @param situationPublishInstanceAddReq 实例新增请求
	 * @return {@code BaseRsp<SituationPublishInstance>}
	 */
	@ApiOperation("实例-新增")
	@PostMapping("")
	public BaseRsp<SituationPublishInstance> setSituationPublishInstance(
			@RequestBody @Validated(Create.class) SituationPublishInstanceAddReq situationPublishInstanceAddReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationPublishInstanceAddReq.getName(),
				situationPublishInstanceAddReq.getDescription());

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.insertOne(situationPublishInstanceAddReq, false);
		return new BaseRsp<>(situationPublishInstance);
	}

	/**
	 * 实例-列表查询
	 *
	 * @param situationPublishInstanceFilterReq 实例过滤请求
	 * @return {@code BaseRsp<ListRsp<SituationPublishInstancePageRes>>}
	 */
	@ApiOperation("实例-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<SituationPublishInstancePageRes>> getSituationPublishInstance(
			SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {

		return new BaseRsp<>(situationPublishInstanceService
				.getSituationPublishInstancePage(situationPublishInstanceFilterReq));
	}

	/**
	 * 获取实例预览
	 *
	 * @param situationPublishInstance 态势实例
	 * @return {@code SituationPublishInstanceRes}
	 */
	private SituationPublishInstanceRes getSituationPublishInstancePreviewRes(
			SituationPublishInstanceRes situationPublishInstance) {
		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.selectById(situationPublishInstance.getAttributeId());
		situationPublishInstance.setStartTime(dashboardAttribute.getStartTime());
		situationPublishInstance.setEndTime(dashboardAttribute.getEndTime());
		return situationPublishInstance;
	}

	/**
	 * 实例-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationPublishInstanceRes>}
	 */
	@ApiOperation("实例-单个查询")
	@GetMapping("")
	public BaseRsp<SituationPublishInstanceRes> getSituationPublishInstanceDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		// 查询实例
		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstance) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 查询实例对应的模板
		String originTemplateId = dashboardTemplateRefMapper
				.selectOriginTemplateId(situationPublishInstance.getTemplateId());
		SituationPublishInstanceRes situationPublishInstanceRes = BeanUtils
				.deepCopy(situationPublishInstance, SituationPublishInstanceRes.class);

		// 查询实例对应的属性
		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstance.getAttributeId());

		// 组装实例属性
		situationPublishInstanceRes.setSchemeName(dashboardAttribute.getSchemeName());
		situationPublishInstanceRes.setSchemeDesc(dashboardAttribute.getSchemeDesc());
		TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(dashboardAttribute.getTaskType())).findFirst()
				.orElse(TaskType.QB);
		situationPublishInstanceRes.setTaskType(type.getDesc());
		situationPublishInstanceRes.setOriginTemplateId(originTemplateId);
		getSituationPublishInstancePreviewRes(situationPublishInstanceRes);
		return new BaseRsp<>(situationPublishInstanceRes);
	}

	/**
	 * 实例-删除
	 *
	 * @param batchDeleteReq 批量删除请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("实例-删除")
	@DeleteMapping("")
	public BaseRsp
			deleteSituationPublishInstance(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationPublishInstance> situationPublishInstances = situationPublishInstanceMapper
				.selectBatchIds(resourceIds);
		if (situationPublishInstances.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 关联发布大屏不可删除
		LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SituationPublishInstance::getStatus,
				SituationInstanceStatus.RELEASE.getCode());
		queryWrapper.in(SituationPublishInstance::getResourceId, resourceIds);
		List<SituationPublishInstance> releaseInstances = situationPublishInstanceMapper
				.selectList(queryWrapper);
		if (!releaseInstances.isEmpty()) {
			throw new BusinessException(
					SituationErrorCode.RELEASE_SITUATION_INSTANCE_CANNOT_DELETE_ERROR);
		}

		// 获取关联模板
		List<String> dashboardTemplateIdList = new ArrayList<>();
		for (SituationPublishInstance releaseInstance : situationPublishInstances) {
			String templateId = releaseInstance.getTemplateId();
			if (StringUtils.isNotBlank(templateId)) {
				dashboardTemplateIdList.add(templateId);
			}
		}

		// 获取关联属性
		List<String> attributeIds = situationPublishInstanceMapper.getAttributeIds(resourceIds);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				situationPublishInstanceMapper.deleteBatchIds(resourceIds);
				dashboardTemplateRefService.deleteDashboardTemplate(dashboardTemplateIdList);
				dashboardAttributeMapper.deleteBatchIds(attributeIds);
			}
		});
		return new BaseRsp<>();
	}

	/**
	 * 实例-修改
	 *
	 * @param situationPublishInstanceUpdateReq 实例修改请求
	 * @return {@code BaseRsp<SituationPublishInstance>}
	 */
	@ApiOperation("实例-修改")
	@PatchMapping("")
	public BaseRsp<SituationPublishInstance> patchSituationPublishInstance(
			@RequestBody @Validated(Update.class) SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(situationPublishInstanceUpdateReq.getName(),
				situationPublishInstanceUpdateReq.getDescription());

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.updateOne(situationPublishInstanceUpdateReq);

		return new BaseRsp<>(situationPublishInstance);
	}

	/**
	 * 实例-发布
	 *
	 * @param situationInstanceReleaseReq 实例发布请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("实例-发布")
	@PostMapping("release")
	public BaseRsp releaseSituationPublishInstance(
			@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {

		// 获取大屏实例
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationPublishInstances.setStatus(SituationInstanceStatus.RELEASE.getCode());

		// 三级大屏没有科目ID时，抛出异常
		String taskId = situationPublishInstances.getTaskId();
		String subjectId = situationPublishInstances.getSubjectId();
		if (StringUtils.isNotBlank(taskId) && StringUtils.isBlank(subjectId)) {
			throw new BusinessException(SituationErrorCode.SUBJECT_EMPTY_ERROR);
		}

		// 修改大屏状态
		situationPublishInstanceService.changeInstancePublicStatus(situationPublishInstances,
				PublicStatus.COMPLETE);

		// 构造推送方案状态变更消息
		StatusOrderContext statusOrderContext = new StatusOrderContext();
		statusOrderContext.setOrderTime(TimeUtil.format1(new Date()));
		statusOrderContext.setZfaid(situationPublishInstances.getSchemeId());
		statusOrderContext.setFazt(PublicStatus.COMPLETE.getName());
		statusOrderContext.setTaskId(situationPublishInstances.getTaskId());
		statusOrderContext.setSubjectId(situationPublishInstances.getSubjectId());
		statusOrderContext.setDescription("方案状态同步");
		statusOrderContext.setSourceSys(StatusOrderContext.KSH_SYS_ID);
		statusOrderContext.setOrderId(IDGeneratorUtil.getID());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 想定大屏发布后调用想定接口进行备案
				if (StringUtils.isNotBlank(subjectId) && situationPublishInstances.getXd()) {
					Boolean result = remoteSituationService.releaseScreen(statusOrderContext);
					if (!result) {
						throw new BusinessException(SituationErrorCode.REMOTE_FAILED_TO_RELEASE);
					}
					// 发布过的想定大屏不允许自行编辑
					situationPublishInstances.setEdit(false);
				}
				situationPublishInstanceMapper.updateById(situationPublishInstances);
			}
		});
		return new BaseRsp<>();
	}

	/**
	 * 实例-取消发布
	 *
	 * @param situationInstanceReleaseReq 实例发布请求
	 * @return {@code BaseRsp}
	 */
	@ApiOperation("实例-取消发布")
	@PostMapping("cancel_release")
	public BaseRsp cancelReleaseSituationPublishInstance(
			@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 修改大屏状态
		situationPublishInstanceService.changeInstancePublicStatus(situationPublishInstances,
				PublicStatus.PREPARATION);

		// 想定大屏不允许主动取消发布
		if (situationPublishInstances.getXd()) {
			throw new BusinessException(SituationErrorCode.XD_DASHBOARD_CANCEL_ERROR);
		}

		situationPublishInstances.setStatus(SituationInstanceStatus.DRAFT.getCode());
		situationPublishInstanceMapper.updateById(situationPublishInstances);
		return new BaseRsp<>();

	}

	/**
	 * 实例-大屏预览
	 *
	 * @param situationInstanceReleaseReq 实例请求
	 * @return {@code SituationPublishInstanceReviewRes}
	 */
	@ApiOperation("实例-大屏预览")
	@PostMapping("preview")
	public SituationPublishInstanceReviewRes previewSituationPublishInstance(
			@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {

		// 获取大屏实例
		String resourceId = situationInstanceReleaseReq.getResourceId();
		String type = situationInstanceReleaseReq.getType();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 获取大屏模板
		String templateId = situationPublishInstances.getTemplateId();
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService
				.getDashboardTemplateInfo(templateId, type, Boolean.TRUE);

		// 组装大屏数据
		SituationPublishInstanceReviewRes copy = BeanUtils.copy(situationPublishInstances,
				SituationPublishInstanceReviewRes.class);
		List<DashboardComponents> dashboardComponentsList = dashboardTemplateRefService
				.exchangeOptionDataSet(copy, new DynamicParameterContext());
		dashboardTemplateInfo.setComponentsSetting(dashboardComponentsList);
		copy.setTemplateInfo(dashboardTemplateInfo);
		return copy;
	}

	/**
	 * 实例-大屏数据
	 *
	 * @param query 查询请求
	 * @return {@code BaseRsp<InstanceDataQueryRes>}
	 */
	@ApiOperation("实例-大屏数据")
	@PostMapping("/query/")
	public BaseRsp<InstanceDataQueryRes>
			getInstanceData(@RequestBody @Validated InstanceDataQueryReq query) {
		// 提取动态参数实际取值
		InstanceDataQueryRes instanceDataQueryRes = new InstanceDataQueryRes();
		DynamicParameterContext context = BeanUtils.copy(query, DynamicParameterContext.class);
		SituationPublishInstance instance = situationPublishInstanceMapper
				.selectById(query.getDashboardId());
		context.setTaskId(instance.getTaskId());
		context.setSubjectId(instance.getSubjectId());
		context.setSubRangeId(instance.getSubRangeId());

		// 依据context中实际参数，替换接口配置中的动态参数，执行请求后返回组件及其数据，动态参数参见situation.constant.DynamicParameter
		String templateId = instance.getTemplateId();
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService
				.getDashboardTemplateInfo(templateId, query.getType(), Boolean.TRUE);
		SituationPublishInstanceReviewRes copy = BeanUtils.copy(instance,
				SituationPublishInstanceReviewRes.class);

		copy.setTemplateInfo(dashboardTemplateInfo);
		List<DashboardComponents> dashboardComponentsList = dashboardTemplateRefService
				.exchangeOptionDataSet(copy, context);
		instanceDataQueryRes.setComponentsSetting(dashboardComponentsList);
		return new BaseRsp<>(instanceDataQueryRes);
	}

	/**
	 * 修改公共状态
	 *
	 * @param dashboardId  大屏ID
	 * @param publicStatus 想定方案状态
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("实例-修改公共状态")
	@GetMapping("/public/status/")
	public BaseRsp<Boolean> changePublicStatus(
			@RequestParam("dashboard_id") @ApiParam(required = true, name = "dashboard_id",
					value = "大屏ID") String dashboardId,
			@RequestParam("public_status") @ApiParam(required = true, name = "public_status",
					value = "状态") Integer publicStatus) {
		return new BaseRsp<>(situationPublishInstanceService.changeInstancePublicStatus(dashboardId,
				PublicStatus.getPublicStatusByCode(publicStatus)));
	}

}