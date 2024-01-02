package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.course.controller.req.ClassroomSignInReq;
import com.cyberpeace.oj.course.controller.req.RemoteHelpReq;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "oj-前台课程表相关")
@RestController
@RequestMapping("/oj/course/class/room")
//@RequiresAuthentication
public class WebClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ClassroomSignService classroomSignService;

	@ApiOperation("前台课程表信息查询")
	@GetMapping("/list")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
//			MenuConstant.WEB_PERSON_CENTER }, logical = Logical.OR)
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

		roomListQuery.setOrgId(authContext.getOrganization());

		List<TimeScheduleBo> boList = classroomService.selectTimeList(roomListQuery, authContext);

		return new BaseRsp<>(boList);
	}

	@ApiOperation("课堂签到")
	@PostMapping("/sign_in")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
//			logical = Logical.OR)
	public BaseRsp<Boolean> classroomSignIn(@RequestBody @Validated ClassroomSignInReq signInReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = classroomSignService.signIn(signInReq.getResourceId(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("请求远程协助")
	@PatchMapping("/need_help")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
//			logical = Logical.OR)
	public BaseRsp<Boolean> needHelp(@RequestBody @Validated RemoteHelpReq req) {

		classroomSignService.needHelp(req.getLessonId(), req.getNodeId(), req.getRoomId());

		return new BaseRsp<>(true);

	}

	@ApiOperation("获取远程协助状态")
	@GetMapping("/help_status")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
//			logical = Logical.OR)
	public BaseRsp<HelpStatusBo> getHelpStatus(RemoteHelpReq req) {
		HelpStatusBo helpStatus = classroomSignService.getHelpStatus(req.getLessonId(),
				req.getNodeId(), req.getRoomId());
		return new BaseRsp<>(helpStatus);
	}

	@ApiOperation("获取课堂聊天用户信息")
	@GetMapping("/chat/users")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
//			logical = Logical.OR)
	public BaseRsp<DashboardInfo> chatUsers(@RequestParam("room_id") String roomId) {

		DashboardInfo dashboardInfo = classroomService.getChatUsers(roomId,
				UserInfoContext.getUserContext());
		return new BaseRsp<>(dashboardInfo);
	}

	@ApiOperation("获取聊天组用户信息")
	@GetMapping("/chat/members_info")
//	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
//			MenuConstant.CMS_CLASS_ROOM }, logical = Logical.OR)
	public BaseRsp<List<DashboardUser>> getGroupUsers(@RequestParam("room_id") String roomId,
			@Param("group_id") String groupId) {
		List<DashboardUser> dashboardUsers = classroomService.getChatGroupUsers(roomId, groupId);
		return new BaseRsp<>(dashboardUsers);
	}

}
