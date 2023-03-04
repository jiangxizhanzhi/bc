package com.cyberpeace.situation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.dao.entity.DashboardComponents;
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
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.*;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateRefMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.context.DynamicParameterContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "组件库-态势发布大屏")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/publish")
public class SituationPublishController {

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

	@ApiOperation("查询态势发布类型列表")
	@GetMapping("/category/list")
	public BaseRsp<List<SituationCategory>> getSituationPublishInstanceCategory() {
		LambdaQueryWrapper<SituationCategory> queryWrapper = new LambdaQueryWrapper<>();
		List<SituationCategory> res = situationCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询态势发布状态列表")
	@GetMapping("/status/list")
	public BaseRsp<List<SituationInstanceStatusRes>> getSituationPublishInstanceStatus() {
		List<SituationInstanceStatusRes> res = new ArrayList<>();
		for (SituationInstanceStatus status : SituationInstanceStatus.values()) {
			SituationInstanceStatusRes copy = BeanUtils.copy(status,
					SituationInstanceStatusRes.class);
			res.add(copy);
		}
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增态势发布实例")
	@PostMapping("")
	public BaseRsp<SituationPublishInstance> setSituationPublishInstance(
			@RequestBody @Validated(Create.class) SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.insertOne(situationPublishInstanceAddReq, false);

		return new BaseRsp<>(situationPublishInstance);
	}

	@ApiOperation("查询态势发布实例列表")
	@GetMapping("list")
	public BaseRsp<ListRsp<SituationPublishInstancePageRes>> getSituationPublishInstance(
			SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {

		return new BaseRsp<>(situationPublishInstanceService
				.getSituationPublishInstancePage(situationPublishInstanceFilterReq));
	}

	@ApiOperation("查询态势发布实例详情")
	@GetMapping("")
	public BaseRsp<SituationPublishInstanceRes> getSituationPublishInstanceDetail(
			@RequestParam("resourceId") @NotBlank String resourceId) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstance) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		String originTemplateId = dashboardTemplateRefMapper
				.selectOriginTemplateId(situationPublishInstance.getTemplateId());
		SituationPublishInstanceRes situationPublishInstanceRes = BeanUtils
				.deepCopy(situationPublishInstance, SituationPublishInstanceRes.class);

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstance.getAttributeId());

		situationPublishInstanceRes.setSchemeName(dashboardAttribute.getSchemeName());
		situationPublishInstanceRes.setSchemeDesc(dashboardAttribute.getSchemeDesc());
		TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(dashboardAttribute.getTaskType())).findFirst()
				.orElse(TaskType.QB);
		situationPublishInstanceRes.setTaskType(type.getDesc());
		situationPublishInstanceRes.setOriginTemplateId(originTemplateId);

		return new BaseRsp<>(situationPublishInstanceRes);
	}

	@ApiOperation("删除态势发布实例")
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

	@ApiOperation("修改态势发布实例")
	@PatchMapping("")
	public BaseRsp<SituationPublishInstance> patchSituationPublishInstance(
			@RequestBody @Validated(Update.class) SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.updateOne(situationPublishInstanceUpdateReq);

		return new BaseRsp<>(situationPublishInstance);
	}

	@ApiOperation("态势实例发布")
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

		// 三级大屏同一科目下仅能有一个大屏发布
		// if (StringUtils.isNotBlank(taskId)) {
		// LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new
		// LambdaQueryWrapper<>();
		// queryWrapper.eq(SituationPublishInstance::getStatus,
		// SituationInstanceStatus.RELEASE.getCode());
		// queryWrapper.eq(SituationPublishInstance::getSubjectId,
		// situationPublishInstances.getSubjectId());
		// SituationPublishInstance one =
		// situationPublishInstanceService.getOne(queryWrapper);
		// if (null != one) {
		// throw new BusinessException(SituationErrorCode.RELEASE_REPEAT_ERROR);
		// }
		// }

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 想定大屏发布后调用想定接口进行备案
				if (StringUtils.isNotBlank(subjectId) && situationPublishInstances.getXd()) {
					Boolean result = remoteSituationService.releaseScreen(
							situationPublishInstances.getSchemeId(),
							situationPublishInstances.getResourceId());
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

	@ApiOperation("态势实例取消发布")
	@PostMapping("cancel_release")
	public BaseRsp cancelReleaseSituationPublishInstance(
			@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 想定大屏不允许主动取消发布
		if (situationPublishInstances.getXd()) {
			throw new BusinessException(SituationErrorCode.XD_DASHBOARD_CANCEL_ERROR);
		}

		situationPublishInstances.setStatus(SituationInstanceStatus.DRAFT.getCode());
		situationPublishInstanceMapper.updateById(situationPublishInstances);
		return new BaseRsp<>();

	}

    @ApiOperation("态势实例预览")
    @PostMapping("preview")
    public SituationPublishInstanceReviewRes previewSituationPublishInstance(@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {
        String resourceId = situationInstanceReleaseReq.getResourceId();
        String type = situationInstanceReleaseReq.getType();
        SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper.selectById(resourceId);
        if (null == situationPublishInstances) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String templateId = situationPublishInstances.getTemplateId();
        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, type, Boolean.TRUE);
        SituationPublishInstanceReviewRes copy = BeanUtils.copy(situationPublishInstances, SituationPublishInstanceReviewRes.class);
        // TODO 大屏地址
        List<DashboardComponents> dashboardComponentsList = dashboardTemplateRefService.exchangeOptionDataSet(copy, new DynamicParameterContext());
        dashboardTemplateInfo.setComponentsSetting(dashboardComponentsList);
        copy.setTemplateInfo(dashboardTemplateInfo);
        return copy;
    }

	@ApiOperation("态势组件实例数据")
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

		// todo 依据context中实际参数，替换接口配置中的动态参数，执行请求后返回组件及其数据，动态参数参见situation.constant.DynamicParameter
        String templateId = instance.getTemplateId();
        DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, query.getType(), Boolean.TRUE);
        SituationPublishInstanceReviewRes copy = BeanUtils.copy(instance, SituationPublishInstanceReviewRes.class);
        // TODO 大屏地址
        copy.setTemplateInfo(dashboardTemplateInfo);
        List<DashboardComponents> dashboardComponentsList = dashboardTemplateRefService.exchangeOptionDataSet(copy, new DynamicParameterContext());
        instanceDataQueryRes.setComponentsSetting(dashboardComponentsList);
		return new BaseRsp<>(instanceDataQueryRes);
	}

}