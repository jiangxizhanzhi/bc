package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.oj.course.controller.req.TrainPlanAddReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;
import com.cyberpeace.oj.course.service.bo.TrainPlanContext;
import com.cyberpeace.oj.course.service.bo.classroom.PlanProgressBo;
import com.cyberpeace.oj.course.service.impl.ClassroomValidateHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-后台训练计划相关api")
@RequiresAuthentication
@RestController
@RequestMapping("/oj/admin/train/plan")
public class AdminTrainPlanController {

	@Autowired
	private TrainPlanService trainPlanService;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@ApiOperation("训练计划列表查询全部")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<TrainPlanBo>> list() {
		List<TrainPlanBo> trainPlanBos = trainPlanService
				.trainPlanList(UserInfoContext.getUserContext());
		return new BaseRsp(trainPlanBos);
	}

	@ApiOperation("训练计划列表分页查询")
	@GetMapping("/list/page")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<TrainPlanRsp>> page(@Validated TrainPlanReq trainPlanReq) {

		// 默认时间排序
		trainPlanReq.setSort("-update_time");

		IPage<TrainPlanRsp> data = trainPlanService.page(trainPlanReq,
				UserInfoContext.getUserContext());

		ListRsp<TrainPlanRsp> listResult = PageUtil.getListResult(data);
		for (TrainPlanRsp trainPlanRsp : listResult.getList()) {
			if (StringUtils.isEmpty(trainPlanRsp.getName())) {
				trainPlanRsp.setName(TrainPlan.TASK_SET);
			}
		}

		return new BaseRsp(listResult);
	}

	@ApiOperation("新增单条训练计划")
	@PostMapping("/train_plan")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String> addTrainPlan(@RequestBody @Validated TrainPlanAddReq trainPlanAddReq) {
		TrainPlan trainPlan = BeanUtils.copy(trainPlanAddReq, TrainPlan.class);

		// 校验name是否已存在
		trainPlanService.checkNameRepeat(trainPlanAddReq.getName(), null,
				UserInfoContext.getUserContext().getUserId());

		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		// 课堂基本数据获取
		List<String> orgIds = classroomValidateHandler
				.validateClassroomOrgHasUser(trainPlan.getOrganizationId());
		trainPlan.setOrganizationIds(JsonUtil.arrayToString(orgIds));

		trainPlanService.save(trainPlan);

		return new BaseRsp<>();
	}

	@ApiOperation("编辑单条训练计划")
	@PutMapping("/train_plan")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String>
			addAssessModel(@RequestBody @Validated TrainPlanUpdateReq trainPlanUpdateReq) {
		TrainPlan trainPlan = BeanUtils.copy(trainPlanUpdateReq, TrainPlan.class);

		// 校验name是否已存在
		trainPlanService.checkNameRepeat(trainPlanUpdateReq.getName(),
				trainPlanUpdateReq.getResourceId(), UserInfoContext.getUserContext().getUserId());

		// 课堂基本数据获取
		List<String> orgIds = classroomValidateHandler
				.validateClassroomOrgHasUser(trainPlan.getOrganizationId());
		trainPlan.setOrganizationIds(JsonUtil.arrayToString(orgIds));

		trainPlanService.updateById(trainPlan);

		return new BaseRsp<>();
	}

	@ApiOperation("查询单条训练计划")
	@GetMapping("/train_plan")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<TrainPlanContext> getAssessModel(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		TrainPlan assessModel = trainPlanService.getById(resource_id);
		TrainPlanContext context = BeanUtils.copy(assessModel, TrainPlanContext.class);
		// 组织信息
		String orgName = remoteOrganizationService.orgNameById(assessModel.getOrganizationId());
		context.setOrganizationName(orgName);

		String orgPath = remoteOrganizationService.getOrgPath(assessModel.getOrganizationId());
		context.setOrgPath(orgPath);

		List<String> parentIds = remoteOrganizationService
				.getParentIds(assessModel.getOrganizationId());
		context.setParentOrgIds(parentIds);

		return new BaseRsp(context);
	}

	@ApiOperation("后台计划批量删除")
	@DeleteMapping("delete_batch")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Integer> batchDelete(@RequestBody @Validated DeleteReq delReq) {

		List<String> ids = classroomService.selectIdsByPlanIds(delReq.getIds());
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				//如果包括临时计划,添加该用户能够看到的临时任务
				if(delReq.getIds().contains(TrainPlan.TASK_SET_ID)){
					ids.addAll(classroomService.selectTempTaskIdsByUser(UserInfoContext.getUserContext())) ;
				}
				trainPlanService.removeByIds(delReq.getIds());

				for (String id : ids) {
					Integer result = classroomService.deleteClassroom(id, authContext);
				}
			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("复用训练计划")
	@PostMapping("/train_plan/reuse")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String> reuseTrainPlan(@RequestBody @Validated TrainPlanUpdateReq reuseReq) {

		trainPlanService.reuse(reuseReq);
		return new BaseRsp<>();
	}

	@ApiOperation("课程进度查看")
	@GetMapping("/progress")
	public BaseRsp<List<PlanProgressBo>>
			getProgress(@ApiParam(value = "教学课程id", name = "plan_id") String plan_id) {
		List<PlanProgressBo> res = trainPlanService.getProgress(plan_id);
		return new BaseRsp<>(res);
	}
}
