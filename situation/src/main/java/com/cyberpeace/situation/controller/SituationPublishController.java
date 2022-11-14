package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.SituationPublishInstanceListRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.entity.SituationCategory;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.dao.mapper.SituationCategoryMapper;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
	private DashboardTemplateService dashboardTemplateService;

	@Autowired
	private SituationCategoryMapper situationCategoryMapper;

	@Autowired
	private DashboardTemplateMapper dashboardTemplateMapper;

	@ApiOperation("查询态势发布类型列表")
	@GetMapping("/category/list")
	public BaseRsp<List<SituationCategory>> getSituationPublishInstanceCategory() {
		LambdaQueryWrapper<SituationCategory> queryWrapper = new LambdaQueryWrapper<>();
		List<SituationCategory> res = situationCategoryMapper.selectList(queryWrapper);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询态势发布状态列表")
	@GetMapping("/status/list")
	public BaseRsp<List<String>> getSituationPublishInstanceStatus() {
		List<String> res = Arrays.stream(SituationInstanceStatus.values()).map(SituationInstanceStatus::getCode).collect(Collectors.toList());
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
		List<SituationPublishInstanceListRes> result = new ArrayList<>();
		res.forEach(s-> {
			// TODO 设置 任务名称及 科目名称
			String taskId = s.getTaskId();
			String subjectId = s.getSubjectId();
			SituationPublishInstanceListRes copy = BeanUtils.copy(s, SituationPublishInstanceListRes.class);
			result.add(copy);

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
		queryWrapper.eq(SituationPublishInstance::getResourceId, resourceIds);
		List<SituationPublishInstance> releaseInstances = situationPublishInstanceMapper.selectList(queryWrapper);
		if (!releaseInstances.isEmpty()) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		situationPublishInstanceMapper.deleteBatchIds(resourceIds);
		return new BaseRsp<>();
	}

	@ApiOperation("修改态势发布实例")
	@PatchMapping("")
	public BaseRsp patchSituationPublishInstance(@RequestBody @Validated(Update.class) SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService.updateOne(situationPublishInstanceUpdateReq);

		return new BaseRsp<>();
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

}