package com.cyberpeace.situation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.situation.constant.TaskType;
import com.cyberpeace.situation.controller.res.*;
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
import com.cyberpeace.integratation.model.situation.SituationPlan;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;

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
	private DashboardTemplateMapper dashboardTemplateMapper;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private TransactionTemplate transactionTemplate;

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
		for(SituationInstanceStatus status:SituationInstanceStatus.values()){
			SituationInstanceStatusRes copy = BeanUtils.copy(status, SituationInstanceStatusRes.class);
			res.add(copy);
		}
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增态势发布实例")
	@PostMapping("")
	public BaseRsp<SituationPublishInstance>
	setSituationPublishInstance(@RequestBody @Validated(Create.class) SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
		SituationPublishInstance situationPublishInstance = situationPublishInstanceService.insertOne(situationPublishInstanceAddReq);

		return new BaseRsp<>(situationPublishInstance);
	}

	@ApiOperation("查询态势发布实例列表")
	@GetMapping("list")
	public BaseRsp<ListRsp<SituationPublishInstancePageRes>>
	getSituationPublishInstance(SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {

		return new BaseRsp<>(situationPublishInstanceService.getSituationPublishInstancePage(situationPublishInstanceFilterReq));
	}

	@ApiOperation("查询态势发布实例详情")
	@GetMapping("")
	public BaseRsp<SituationPublishInstanceRes>
	getSituationPublishInstanceDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(resourceId);
		if(null == situationPublishInstance){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SituationPublishInstanceRes situationPublishInstanceRes = BeanUtils.deepCopy(situationPublishInstance, SituationPublishInstanceRes.class);

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getByResourceId(situationPublishInstance.getAttributeId());

		situationPublishInstanceRes.setSchemeName(dashboardAttribute.getSchemeName());
		TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(dashboardAttribute.getTaskType())).findFirst().orElse(TaskType.QB);
		situationPublishInstanceRes.setTaskType(type.getDesc());

		return new BaseRsp<>(situationPublishInstanceRes);
	}

	@ApiOperation("删除态势发布实例")
	@DeleteMapping("")
	public BaseRsp deleteSituationPublishInstance(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {
		List<String> resourceIds = batchDeleteReq.getIds();
		List<SituationPublishInstance> situationPublishInstances = situationPublishInstanceMapper.selectBatchIds(resourceIds);
		if (situationPublishInstances.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 关联发布大屏不可删除
		LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SituationPublishInstance::getStatus, SituationInstanceStatus.RELEASE.getCode());
		queryWrapper.in(SituationPublishInstance::getResourceId, resourceIds);
		List<SituationPublishInstance> releaseInstances = situationPublishInstanceMapper.selectList(queryWrapper);
		if (!releaseInstances.isEmpty()) {
			throw new BusinessException(SituationErrorCode.RELEASE_SITUATION_INSTANCE_CANNOT_DELETE_ERROR);
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
	public BaseRsp<SituationPublishInstance> patchSituationPublishInstance(@RequestBody @Validated(Update.class) SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService.updateOne(situationPublishInstanceUpdateReq);

		return new BaseRsp<>(situationPublishInstance);
	}

	@ApiOperation("态势实例发布")
	@PostMapping("release")
	public BaseRsp releaseSituationPublishInstance(@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationPublishInstances.setStatus(SituationInstanceStatus.RELEASE.getCode());
		//没有科目id时，默认可以发布
		String subjectId = situationPublishInstances.getSubjectId();
		if (StringUtils.isNotBlank(subjectId)) {
			// TODO发布逻辑暂定 获取当前科目下仅 能有一个大屏发布
			LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(SituationPublishInstance::getStatus, SituationInstanceStatus.RELEASE.getCode());
			queryWrapper.eq(SituationPublishInstance::getSubjectId, situationPublishInstances.getSubjectId());
			SituationPublishInstance one = situationPublishInstanceService.getOne(queryWrapper);
			if (null != one) {
				throw new BusinessException(SituationErrorCode.RELEASE_REPEAT_ERROR);
			}
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				situationPublishInstanceMapper.updateById(situationPublishInstances);
				Boolean result = remoteSituationService.releaseScreen(situationPublishInstances.getSchemeId(),
						situationPublishInstances.getResourceId());
				if (!result) {
					throw new BusinessException(SituationErrorCode.REMOTE_FAILED_TO_RELEASE);
				}
			}
		});
		return new BaseRsp<>();
	}

	@ApiOperation("态势实例取消发布")
	@PostMapping("cancel_release")
	public BaseRsp cancelReleaseSituationPublishInstance(@RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) {
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
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
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId, type);
		SituationPublishInstanceReviewRes copy = BeanUtils.copy(situationPublishInstances, SituationPublishInstanceReviewRes.class);
		// TODO 大屏地址
		copy.setTemplateInfo(dashboardTemplateInfo);
		dashboardTemplateRefService.exchangeOptionDataSet(copy);
		return copy;
	}

}