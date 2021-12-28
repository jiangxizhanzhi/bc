package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.ClassroomService;

import io.swagger.annotations.Api;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Api(tags = "oj-前台课程表相关")
@RestController
@RequestMapping("/oj/course/class/room")
@RequiresAuthentication
public class WebClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@ApiOperation("前台课程表信息查询")
	@GetMapping("/list")
	public BaseRsp<List<ClassroomBo>> listCourseSchedule(
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
		roomListQuery.setOrgId(authContext.getOrganization());

		List<ClassroomBo> boList = classroomService.selectList(roomListQuery, authContext);

		return new BaseRsp(boList);
	}

}
