package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.situation.SituationPlan;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationInstanceStatusRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceListRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceReviewRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
		// TODO Gis等数据是否需要大屏 存入模板实例
		SituationPublishInstance situationPublishInstance = situationPublishInstanceService.insertOne(situationPublishInstanceAddReq);


		return new BaseRsp<>(situationPublishInstance);
	}

	@ApiOperation("查询态势发布实例列表")
	@GetMapping("list")
	public BaseRsp<List<SituationPublishInstanceListRes>>
	getSituationPublishInstance(SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {
		LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
		String name = situationPublishInstanceFilterReq.getName();
		if (null != name){
			queryWrapper.like(SituationPublishInstance::getName, name);
		}

		String category = situationPublishInstanceFilterReq.getCategory();
		if (null != category){
			// TODO 获取对应任务类型对应的任务列表
			queryWrapper.in(SituationPublishInstance::getTaskId, category);
		}

		String status = situationPublishInstanceFilterReq.getStatus();
		if(null != status){
			List<String> statusLs = Arrays.stream(SituationInstanceStatus.values()).map(SituationInstanceStatus::getCode).collect(Collectors.toList());
			if(!statusLs.contains(status)){
				throw new BusinessException(SituationErrorCode.STATUS_INVALID_ERROR);
			}
			queryWrapper.eq(SituationPublishInstance::getStatus, status);
		}

		queryWrapper.orderByDesc(SituationPublishInstance::getCreateTime);
		List<SituationPublishInstance> res = situationPublishInstanceMapper.selectList(queryWrapper);


		LambdaQueryWrapper<DashboardTemplate> dashboardTemplateLambdaQueryWrapper = new LambdaQueryWrapper<>();
		List<DashboardTemplate> dashboardTemplates = dashboardTemplateMapper.selectList(dashboardTemplateLambdaQueryWrapper);
		Map<String, DashboardTemplate> dashboardTemplateMap = dashboardTemplates.stream().collect(Collectors.toMap(DashboardTemplate::getResourceId, dashboardTemplate -> dashboardTemplate));

		List<SituationPublishInstanceListRes> result = new ArrayList<>();
		List<SituationPlan> plans = remoteSituationService.getPlans();
		Map<String, SituationPlan> planMaps = plans.stream().collect(Collectors.toMap(SituationPlan::getResourceId, plan -> plan));
		List<SituationTask> tasks = remoteSituationService.getTasks(null, null);
		Map<String, SituationTask> taskMaps = tasks.stream().collect(Collectors.toMap(SituationTask::getTask_id, task -> task));
		List<String> taskIds = StreamApiUtils.getColumn(res, SituationPublishInstance::getTaskId);
		List<SituationSubject> subjects = remoteSituationService.getSubjectByTasks(taskIds);
		Map<String, SituationSubject> subjectMaps = subjects.stream().collect(Collectors.toMap(SituationSubject::getSubject_id, subject -> subject));
		LambdaQueryWrapper<SituationCategory> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
		List<SituationCategory> situationCategories = situationCategoryMapper.selectList(categoryLambdaQueryWrapper);
		Map<String, SituationCategory> collect = situationCategories.stream().collect(Collectors.toMap(SituationCategory::getResourceId, situation -> situation));
		HashMap<String, String> map = new HashMap<>();
		for(SituationInstanceStatus tmp:SituationInstanceStatus.values()){
			map.put(tmp.getCode(), tmp.getDesc());
		}


		res.forEach(s-> {
			SituationPublishInstanceListRes copy = BeanUtils.copy(s, SituationPublishInstanceListRes.class);
//			String planName = null == planMaps.get(s.getPlanId()) ? "":planMaps.get(s.getPlanId()).getName();
//			copy.setPlanName(planName);
			String taskName = null == taskMaps.get(s.getTaskId()) ? "" : taskMaps.get(s.getTaskId()).getTask_name();
			copy.setTaskName(taskName);
			String subjectName = null == subjectMaps.get(s.getSubjectId()) ? "" : subjectMaps.get(s.getSubjectId()).getSubject_name();
			copy.setSubjectName(subjectName);
			copy.setStatus(map.get(s.getStatus()));
			copy.setCategory(null == s.getCategory() || null == collect.get(s.getCategory()) ? "" : collect.get(s.getCategory()).getName());
			copy.setTemplateName(null == s.getTemplateId() ||null == dashboardTemplateMap.get(s.getTemplateId()) ? "" :dashboardTemplateMap.get(s.getTemplateId()).getName());
			result.add(copy);
//
			//清除多余数据
			situationPublishInstanceService.deleteUnusedSituation();
		});
		return new BaseRsp<>(result);
	}

	@ApiOperation("查询态势发布实例详情")
	@GetMapping("")
	public BaseRsp<SituationPublishInstance>
	getSituationPublishInstanceDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(resourceId);
		if(null == situationPublishInstance){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return new BaseRsp<>(situationPublishInstance);
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
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		List<String> dashboardTemplateIdList = new ArrayList<>();
		for (SituationPublishInstance releaseInstance : situationPublishInstances) {
			String templateId = releaseInstance.getTemplateId();
			if (StringUtils.isNotBlank(templateId)) {
				dashboardTemplateIdList.add(templateId);
			}
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				situationPublishInstanceMapper.deleteBatchIds(resourceIds);
				dashboardTemplateRefService.deleteDashboardTemplate(dashboardTemplateIdList);
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
		// TODO发布逻辑暂定 获取当前科目下仅 能有一个大屏发布
		LambdaQueryWrapper<SituationPublishInstance> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SituationPublishInstance::getStatus, SituationInstanceStatus.RELEASE.getCode());
		queryWrapper.eq(SituationPublishInstance::getSubjectId, situationPublishInstances.getSubjectId());
		SituationPublishInstance one = situationPublishInstanceService.getOne(queryWrapper);
		if (null != one) {
			throw new BusinessException(SituationErrorCode.RELEASE_REPEAT_ERROR);
		}

		situationPublishInstanceMapper.updateById(situationPublishInstances);
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
	public SituationPublishInstanceReviewRes previewSituationPublishInstance(HttpServletResponse res, @RequestBody @Validated SituationInstanceReleaseReq situationInstanceReleaseReq) throws IOException {
		String resourceId = situationInstanceReleaseReq.getResourceId();
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper.selectById(resourceId);
		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String templateId = situationPublishInstances.getTemplateId();
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService.getDashboardTemplateInfo(templateId);
		SituationPublishInstanceReviewRes copy = BeanUtils.copy(situationPublishInstances, SituationPublishInstanceReviewRes.class);
		// TODO 大屏地址
//		res.sendRedirect("page/cr/admin/situation-release/preview/"+ templateId);
		copy.setTemplateInfo(dashboardTemplateInfo);
		return copy;
	}


	@ApiOperation("想定方案list")
	@GetMapping("plan")
	public List<SituationPlan> getPlans() {
		return remoteSituationService.getPlans();
	}

	@ApiOperation("任务list")
	@GetMapping("task")
	public List<SituationTask> getTasks(SituationTaskFilterReq situationTaskFilterReq) {
		// TODO 待修改

		return remoteSituationService.getTasks(situationTaskFilterReq.getType(), null);
	}

	@ApiOperation("科目list")
	@PostMapping("plan")
	public List<SituationSubject> getSubjects(SituationSubjectFilterReq situationSubjectFilterReq) {
		String taskId = situationSubjectFilterReq.getResourceId();
		return remoteSituationService.getSubjectByTask(taskId);
	}

}