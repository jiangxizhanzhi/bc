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
import com.cyberpeace.oj.course.service.bo.ClassRoomWebBo;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Web课堂Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
@Api(tags = "oj-前台课程表相关")
@RestController
@RequestMapping("/oj/course/class/room")
@RequiresAuthentication
@Slf4j
public class WebClassroomController {

	/**
	 * 课堂Service
	 *
	 * @see ClassroomService
	 */
	@Autowired
	private ClassroomService classroomService;

	/**
	 * 课堂标志Service
	 *
	 * @see ClassroomSignService
	 */
	@Autowired
	private ClassroomSignService classroomSignService;

	/**
	 * 列表课程附表
	 *
	 * @param month 月
	 * @return {@code BaseRsp<List<TimeScheduleBo>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<TimeScheduleBo>>
	 */
	@ApiOperation("前台课程表信息查询")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
			MenuConstant.WEB_PERSON_CENTER }, logical = Logical.OR)
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

	/**
	 * 列表课程附表-教学课程
	 * @param startTime 课程开始时间
	 * @param endTime 课程结束时间
	 * @return
	 */
	@ApiOperation("前台课程表信息查询")
	@GetMapping("/course/list")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE }, logical = Logical.OR)
	public BaseRsp<List<ClassRoomWebBo>> listCourseSchedule(
			@ApiParam(name = "start_time", value = "开始时间 格式为xxxx-xx-xx") @RequestParam("start_time") String startTime,
			@ApiParam(name = "end_time", value = "结束时间 格式为xxxx-xx-xx") @RequestParam("end_time") String endTime) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Date startOfDay = TimeUtil.getStartOfDay(
				TimeUtil.toDate2(startTime));
		Date endOfDay = TimeUtil.getEndOfDay(
				TimeUtil.toDate2(endTime));

		if (!authContext.getSuperAdmin() && StringUtils.isBlank(authContext.getOrganization())) {
			log.warn("not super admin and not user organization");
			return new BaseRsp<>();
		}

		// 设置查询条件
		ClassRoomListQuery roomListQuery = new ClassRoomListQuery();
		roomListQuery.setStartTime(startOfDay);
		roomListQuery.setEndTime(endOfDay);

		roomListQuery.setOrgId(authContext.getOrganization());
		final List<ClassRoomWebBo> timeScheduleBos = classroomService.selectTimeListOnlyCourse(roomListQuery, authContext);
		return new BaseRsp<>(timeScheduleBos);
	}

	/**
	 * 课堂登录
	 *
	 * @param signInReq 标志在请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ClassroomSignInReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("课堂签到")
	@PostMapping("/sign_in")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
			logical = Logical.OR)
	public BaseRsp<Boolean> classroomSignIn(@RequestBody @Validated ClassroomSignInReq signInReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = classroomSignService.signIn(signInReq.getResourceId(), authContext);

		return new BaseRsp<>(result);
	}

	/**
	 * 需要帮助
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see RemoteHelpReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("请求远程协助")
	@PatchMapping("/need_help")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
			logical = Logical.OR)
	public BaseRsp<Boolean> needHelp(@RequestBody @Validated RemoteHelpReq req) {

		classroomSignService.needHelp(req.getLessonId(), req.getNodeId(), req.getRoomId());

		return new BaseRsp<>(true);

	}

	/**
	 * 获取帮助状态
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<HelpStatusBo> }
	 * @throws
	 * @see RemoteHelpReq
	 * @see BaseRsp<HelpStatusBo>
	 */
	@ApiOperation("获取远程协助状态")
	@GetMapping("/help_status")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
			logical = Logical.OR)
	public BaseRsp<HelpStatusBo> getHelpStatus(RemoteHelpReq req) {
		HelpStatusBo helpStatus = classroomSignService.getHelpStatus(req.getLessonId(),
				req.getNodeId(), req.getRoomId());
		return new BaseRsp<>(helpStatus);
	}

	/**
	 * 聊天用户
	 *
	 * @param roomId 房间id
	 * @return {@code BaseRsp<DashboardInfo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<DashboardInfo>
	 */
	@ApiOperation("获取课堂聊天用户信息")
	@GetMapping("/chat/users")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE },
			logical = Logical.OR)
	public BaseRsp<DashboardInfo> chatUsers(@RequestParam("room_id") String roomId) {

		DashboardInfo dashboardInfo = classroomService.getChatUsers(roomId,
				UserInfoContext.getUserContext());
		return new BaseRsp<>(dashboardInfo);
	}

	/**
	 * 获取组用户
	 *
	 * @param roomId  房间id
	 * @param groupId 组id
	 * @return {@code BaseRsp<List<DashboardUser>> }
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<List<DashboardUser>>
	 */
	@ApiOperation("获取聊天组用户信息")
	@GetMapping("/chat/members_info")
	@RequiresPermissions(value = { MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
			MenuConstant.CMS_CLASS_ROOM }, logical = Logical.OR)
	public BaseRsp<List<DashboardUser>> getGroupUsers(@RequestParam("room_id") String roomId,
			@Param("group_id") String groupId) {
		List<DashboardUser> dashboardUsers = classroomService.getChatGroupUsers(roomId, groupId);
		return new BaseRsp<>(dashboardUsers);
	}

}
