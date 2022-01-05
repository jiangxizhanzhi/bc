package com.cyberpeace.oj.course.controller.web;

import java.util.Date;
import java.util.List;

import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.course.controller.req.ClassroomSignInReq;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-前台课程表相关")
@RestController
@RequestMapping("/oj/course/class/room")
@RequiresAuthentication
public class WebClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ClassroomSignService classroomSignService;

	@ApiOperation("前台课程表信息查询")
	@GetMapping("/list")
	public BaseRsp<List<TimeScheduleBo>> listCourseSchedule(
			@ApiParam(name = "month", value = "月份 格式xxxx-xx") @RequestParam("month") String month) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Date startOfDay = TimeUtil.getStartOfDay(
				TimeUtil.toDate2(TimeUtil.getHeadOrTailDayOfSepecificMonth(month, true)));
		Date endOfDay = TimeUtil.getEndOfDay(
				TimeUtil.toDate2(TimeUtil.getHeadOrTailDayOfSepecificMonth(month, false)));

		if (!authContext.getSuperAdmin() && StringUtils.isBlank(authContext.getOrganization())) {
			return new BaseRsp<>();
		}

		// 设置查询条件
		ClassRoomListQuery roomListQuery = new ClassRoomListQuery();
		roomListQuery.setStartTime(startOfDay);
		roomListQuery.setEndTime(endOfDay);

		// 管理员查询自己创建和组织下的
		if (authContext.getSuperAdmin() && !authContext.getSuperAdmin()) {
			roomListQuery.setUserId(authContext.getUserId());
			roomListQuery.setOrgId(authContext.getOrganization());
		}

		// 普通用户仅查询组织下的
		if (!authContext.getSuperAdmin() && !authContext.getSuperAdmin()) {
			roomListQuery.setOrgId(authContext.getOrganization());
		}

		List<TimeScheduleBo> boList = classroomService.selectTimeList(roomListQuery, authContext);

		return new BaseRsp(boList);
	}

	@ApiOperation("课堂签到")
	@PostMapping("/sign_in")
	public BaseRsp<Boolean> classroomSignIn(@RequestBody @Validated ClassroomSignInReq signInReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = classroomSignService.signIn(signInReq.getResourceId(), authContext);

		return new BaseRsp<>(result);
	}

}
