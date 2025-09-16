package com.cyberpeace.oj.course.controller.web;

import java.util.*;

import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.res.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.BehaviorRecord;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Web课程Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
@Api(tags = "oj-前台课程相关api")
@RestController
@RequestMapping("/oj/course")
@RequiresAuthentication
public class WebCourseController {

	/**
	 * 课程Service
	 *
	 * @see CourseService
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课程方向Service
	 *
	 * @see CourseCategoryService
	 */
	@Autowired
	private CourseCategoryService courseDirectionService;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private TeamTeacherService teamTeacherService;

	/**
	 * 靶场得分Service
	 */
	@Autowired
	private CrScoreService crScoreService;

	@Autowired
	private RemoteUserService remoteUserService;




	/**
	 * 获取Web课程
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<CourseWebBo>> }
	 * @throws
	 * @see CourseListReq
	 * @see BaseRsp<ListRsp<CourseWebBo>>
	 */
	@ApiOperation("前台课程列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<ListRsp<CourseWebBo>> getWebCourse(
			@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {
		// 数据组装
		CourseQueryBo context = BeanUtils.copy(listReq, CourseQueryBo.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		if (StringUtils.isNotBlank(listReq.getDirection())) {
			List<String> childrenIds = courseDirectionService.getChildrenIds(listReq.getDirection(),
					CommonConstant.TRUE);
			context.setDirections(childrenIds);
		}

		Page<Course> page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		// 数据查询
		ListRsp<CourseWebBo> boListRsp = courseService.selectPage(page, context, authContext, true);
		return new BaseRsp<>(boListRsp);
	}

	/**
	 * 获取课程
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<WebCourseRes> }
	 * @throws
	 * @see String
	 * @see BaseRsp<WebCourseRes>
	 */
	@GetMapping("")
	@ApiOperation("单条查询")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	@BehaviorRecord(module = BehaviorModuleEnum.COURSE, action = BehaviorTypeEnum.ACCESS)
	public BaseRsp<WebCourseRes> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		CourseInfoBo course = courseService.selectById(resourceId, authContext);

		WebCourseRes courseRes = BeanUtils.copy(course, WebCourseRes.class);
        return new BaseRsp<>(courseRes);
	}


	@GetMapping("/single/statistics")
	@ApiOperation("单个课程的统计数据")
	public BaseRsp<SingleCourseStatisticsRes> getSingleCourseStatisticsRes(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		CourseInfoBo course = courseService.selectById(resourceId, authContext);

		SingleCourseStatisticsRes singleCourseStatisticsRes = new SingleCourseStatisticsRes();
		singleCourseStatisticsRes.setResourceId(resourceId);
		singleCourseStatisticsRes.setStudentNums(course.getStudentNum());

		List<TeamRoomRsp> teamRoomRsps = course.getTeamRoomRsps();
		int courseTrainHourSum = 0;
		int courseSubmitMissionSum = 0;
		int courseSuccessMissionSum = 0;
		int courseFinishedPersonNums = 0;
		List<Integer> scores = new ArrayList<>();
		Map<String, SingleCourseUserInfoRes> singleCourseUserInfoResMap = new HashMap<>(8);
		for (TeamRoomRsp teamRoomRsp : teamRoomRsps) {
			int trainHours = trainRecordService.getTimeCostHours(teamRoomRsp.getResourceId());
			courseTrainHourSum = courseTrainHourSum + trainHours;

			TaskStatisticsInfo taskStatisticsInfo = teamTeacherService.getTaskStatisticsInfo(teamRoomRsp.getResourceId());
			courseSubmitMissionSum = courseSubmitMissionSum + taskStatisticsInfo.getSubmitMissionCount();
			courseSuccessMissionSum = courseSuccessMissionSum + taskStatisticsInfo.getSuccessMissionsCount();


			List<StudentScoreListRsp> res = teamTeacherService.getScoreList(teamRoomRsp.getResourceId());

			for (StudentScoreListRsp studentScoreListRsp : res) {
				StudentScoreRsp rsp = crScoreService.getStudentScore(teamRoomRsp.getResourceId(), studentScoreListRsp.getUserId());
				if (Boolean.TRUE.equals(studentScoreListRsp.getScored())) {
					courseFinishedPersonNums = courseFinishedPersonNums + 1;
				}
				if (Objects.nonNull(studentScoreListRsp.getScore())) {
					scores.add(studentScoreListRsp.getScore());
				}
				Integer finishedTasks = rsp.getTaskStatistics().getMissionCount() - rsp.getTaskStatistics().getUnfinished();
				Boolean lessonFinishedFlag = Boolean.TRUE.equals(studentScoreListRsp.getScored());
				Integer score = studentScoreListRsp.getScore();
				singleCourseUserInfoResMap.computeIfAbsent(studentScoreListRsp.getUserId(), k -> new SingleCourseUserInfoRes())
						.addLessonData(studentScoreListRsp.getUserId(), finishedTasks, lessonFinishedFlag, trainHours, score, remoteUserService);
			}
		}

		singleCourseStatisticsRes.setCourseFinishedNums(courseFinishedPersonNums);
		singleCourseStatisticsRes.setScoreStatistics(new TrainGradesRes(scores));
		singleCourseStatisticsRes.setTrainTime(courseTrainHourSum);
		singleCourseStatisticsRes.setTrainTaskStatistics(new CourseTrainTaskRes(courseSubmitMissionSum, courseSuccessMissionSum));

		List<SingleCourseUserInfoRes> singleCourseUserInfoResList = new ArrayList<>(singleCourseUserInfoResMap.values());
		singleCourseUserInfoResList.sort(Comparator.comparing(SingleCourseUserInfoRes::getAverageTrainScore));
		singleCourseStatisticsRes.setPersonStatistics(singleCourseUserInfoResList);

		return new BaseRsp<>(singleCourseStatisticsRes);
	}


	@GetMapping("/user/course/statistics")
	@ApiOperation("用户学习的课程统计数据")
	public BaseRsp<MultiCourseUserinfoRes> getMultiCourseUserinfoRes(@RequestParam("user_id") String userId) {
		return new BaseRsp<>(new MultiCourseUserinfoRes());
	}


	@GetMapping("/user/course/progress")
	@ApiOperation("用户学习课程的进度")
	public BaseRsp<ListRsp<UserCourseProcessRes>> listUserCourseProcessRes(@RequestParam("name") String courseName, @RequestParam("page") Integer page,
																		   @RequestParam("per_page") Integer pageSize) {
		return new BaseRsp<>(new ListRsp<>(new ArrayList<>(), 0, page, pageSize));
	}



	/**
	 * 获取课程
	 *
	 * @return {@code BaseRsp<CourseStatisticsRes> }
	 * @throws
	 * @see String
	 * @see BaseRsp<CourseStatisticsRes>
	 */
	@GetMapping("/statistics")
	@ApiOperation("统计")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<CourseStatisticsRes> statistics() {

		CourseQueryBo context = new CourseQueryBo();
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		Page<Course> page = new Page<>(1,9999);
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		// 数据查询
		ListRsp<CourseWebBo> boListRsp = courseService.selectPage(page, context, authContext, true);
		CourseStatisticsRes courseRes = courseService.statistics(boListRsp, authContext);
		return new BaseRsp<>(courseRes);
	}

}
