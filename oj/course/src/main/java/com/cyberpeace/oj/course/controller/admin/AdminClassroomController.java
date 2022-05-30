package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.oj.course.controller.req.ClassroomDeleteReq;
import com.cyberpeace.oj.course.controller.req.ClassroomListReq;
import com.cyberpeace.oj.course.controller.req.ClassroomMonitorReq;
import com.cyberpeace.oj.course.controller.req.ClassroomReq;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.ClassroomMonitorBo;
import com.cyberpeace.oj.course.service.bo.ClassroomReportBo;
import com.cyberpeace.oj.course.service.bo.ClassroomStatisticBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-后台课程表api")
@RestController
@RequestMapping("/oj/admin/course/classroom")
@RequiresAuthentication
@Slf4j
public class AdminClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Autowired
	private ClassroomSignService classroomSignService;

	@Autowired
	private GxTaskService gxTaskService;

	@ApiOperation("后台课程表查询")
	@GetMapping("/list")
	public BaseRsp<List<ClassroomBo>> list(@Validated ClassroomListReq classroomListReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		if (!authContext.getSuperAdmin() && StringUtils.isBlank(authContext.getOrganization())) {
			return new BaseRsp<>();
		}

		ClassRoomListQuery roomListQuery = BeanUtils.copy(classroomListReq,
				ClassRoomListQuery.class);

		// 查询数据组装
		if (StringUtils.isNotBlank(classroomListReq.getOrganizationId())) {
			List<String> orgIds = classroomService.getOrgIds(classroomListReq.getOrganizationId());
			roomListQuery.setChildrenOrg(orgIds);
		}

		// 非超级管理员仅查看自己创建的课程表
		if (!authContext.getSuperAdmin()) {
			roomListQuery.setUserId(authContext.getUserId());
		}

		// 数据查询
		List<ClassroomBo> classroomBos = classroomService.selectList(roomListQuery, authContext);

		return new BaseRsp<>(classroomBos);
	}

	@ApiOperation("后台课程表创建")
	@PostMapping("")
	public BaseRsp
			createClassRoom(@RequestBody @Validated(Create.class) ClassroomReq classroomReq) {

		// 如果传入为用户列表，转为组id
		if (classroomReq.getUserIds() != null && !classroomReq.getUserIds().isEmpty()) {
			classroomReq.setOrganizationId(
					gxTaskService.createTaskOrg(classroomReq.getUserIds(), null, "课堂组"));
		}

		// 校验组织id是否存在
		if (StringUtils.isEmpty(classroomReq.getOrganizationId())) {
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		classroom.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		Classroom insertResult = classroomService.create(classroom, authContext);

		return new BaseRsp(insertResult);
	}

	@ApiOperation("后台课程表信息修改")
	@PatchMapping("")
	public BaseRsp<Boolean>
			updateClassRoom(@RequestBody @Validated(Update.class) ClassroomReq classroomReq) {

		// 如果传入为用户列表，转为组id
		if (classroomReq.getUserIds() != null && !classroomReq.getUserIds().isEmpty()) {
			classroomReq.setOrganizationId(gxTaskService.createTaskOrg(classroomReq.getUserIds(),
					classroomReq.getResourceId(), "课堂组"));
		}

		// 校验组织id是否存在
		if (StringUtils.isEmpty(classroomReq.getOrganizationId())) {
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = classroomService.updateClassroom(classroom, authContext);
		return new BaseRsp(result);
	}

	@ApiOperation("后台课程表信息删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteClassroom(@RequestBody @Validated ClassroomDeleteReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Integer result = classroomService.deleteClassroom(delReq.getResourceId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("课堂监控获取用户签到列表")
	@GetMapping("/classroom_monitor")
	public BaseRsp<ListRsp<ClassroomMonitorBo>> classroomMonitor(ClassroomMonitorReq monitorReq) {

		Page page = monitorReq.toPageParam();
		ListRsp<ClassroomMonitorBo> classroomMonitorBoList = classroomService.selectSignList(page,
				monitorReq.getClassroomId());
		return new BaseRsp(classroomMonitorBoList);

	}

	@ApiOperation("进行远程协助")
	@GetMapping("/to_help")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resource_id", value = "课堂编号"),
			@ApiImplicitParam(name = "user_id", value = "用户编号") })
	public BaseRsp toHelp(@RequestParam("resource_id") String classroomId,
			@RequestParam("user_id") String userId) {

		classroomSignService.toHelp(classroomId, userId);
		return new BaseRsp();
	}

	@ApiOperation("查询课堂实验报告列表")
	@GetMapping("/classroom_report")
	public BaseRsp<ListRsp<ClassroomReportBo>> classroomReport(ClassroomMonitorReq reportReq) {

		Page page = reportReq.toPageParam();
		List<ClassroomReportBo> classroomReportBos = classroomService
				.selectClassroomReport(reportReq.getClassroomId(), reportReq.getPattern());
		return new BaseRsp(
				PageUtil.getListResult(classroomReportBos, page, classroomReportBos.size()));
	}

	@ApiOperation("课堂学习统计")
	@GetMapping("/classroom_statistic")
	public BaseRsp<ClassroomStatisticBo>
			classroomStatistic(@RequestParam("classroom_id") String classroomId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		ClassroomStatisticBo statisticBo = classroomService.classroomStatistic(classroomId,
				authContext);
		return new BaseRsp<>(statisticBo);
	}

}
