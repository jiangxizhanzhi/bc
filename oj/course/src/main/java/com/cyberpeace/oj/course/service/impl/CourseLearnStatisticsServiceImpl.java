package com.cyberpeace.oj.course.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseLearnStatisticsMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import org.springframework.util.CollectionUtils;

/**
 * 课程学习统计学Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseLearnStatisticsService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseLearnStatisticsServiceImpl` 的服务类，实现了 `CourseLearnStatisticsService` 接口。这个服务类主要用于处理与课程学习统计相关的业务逻辑。下面是对代码的主要功能和结构的简要分析： 
 * ### 类结构 
 * - **类名**：`CourseLearnStatisticsServiceImpl` 
 * - **注释**：详细说明了类的功能、作者、版本等信息。 
 * - **依赖注入**：通过 Spring 的 `@Autowired` 注入了多个 Mapper 和 Service。 
 * ### 主要方法 
 * 1. **`courseHistogram(AuthContext authContext)`** 
 * - 功能：生成课程的直方图数据。 
 * - 输入：认证上下文。 
 * - 输出：直方图结果对象 `HistogramRes`。 
 * 2. **`directionHistogram(AuthContext authContext)`** 
 * - 功能：生成方向（可能是指课程分类）的直方图数据。 
 * - 输入：认证上下文。 
 * - 输出：直方图结果对象 `HistogramRes`。 
 * 3. **`courseStatisticsList(CourseContext context, Page page, AuthContext authContext)`** 
 * - 功能：获取课程统计列表。 
 * - 输入：上下文、分页参数、认证上下文。 
 * - 输出：包含课程统计结果的分页响应对象 `ListRsp<AdminCourseStatisticsRes>`。 
 * 4. **`curseStatisticToBo(AdminCourseStatisticsRes adminCourseStatisticsRes)`** 
 * - 功能：将课程统计结果转换为业务对象。 
 * - 输入：课程统计结果对象。 
 * - 输出：转换后的业务对象。 
 * 5. **`LessonStatisticsList(String courseId, Page page, LessonStatisticsListReq pageReq)`** 
 * - 功能：获取课程中各个课时的统计列表。 
 * - 输入：课程ID、分页参数、课时统计请求体。 
 * - 输出：包含课时统计结果的分页响应对象 `ListRsp<AdminLessonStatisticsRes>`。 
 * 6. **`LessonStatisticsDetail(String organizationId, String lessonId, String usernameLike, Page page, String sort)`** 
 * - 功能：获取特定课程的课时详细统计信息。 
 * - 输入：组织ID、课时ID、用户名模糊匹配、分页参数、排序字段。 
 * - 输出：包含课时详细统计信息的分页响应对象 `ListRsp<AdminLessonStatisticsDetailRes>`。 
 * 7. **`getUsers(String organizationId, String usernameLike)`** 
 * - 功能：根据组织ID和用户名模糊匹配获取用户ID列表。 
 * - 输入：组织ID、用户名模糊匹配。 
 * - 输出：用户ID列表。 
 * ### 辅助方法 
 * - **`getCourseHistogramRes(List<Course> courses)`** 
 * - 功能：生成课程直方图结果对象。 
 * - 输入：课程列表。 
 * - 输出：直方图结果对象 `HistogramRes`。 
 * ### 总结 
 * 该服务类主要负责处理课程及其课时的学习统计数据，包括生成直方图、获取统计列表和详细信息等功能。通过依赖注入的方式使用了多个 Mapper 和 Service 来访问数据库和远程服务，并利用 Java 8 的流式 API 进行数据处理。 
**/
@Service
public class CourseLearnStatisticsServiceImpl implements CourseLearnStatisticsService {

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 课程学习统计学Mapper
	 *
	 * @see CourseLearnStatisticsMapper
	 */
	@Autowired
	private CourseLearnStatisticsMapper courseLearnStatisticsMapper;

	/**
	 * 课程直方图
	 *
	 * @param authContext auth 上下文
	 * @return {@code HistogramRes }
	 * @throws
	 * @see AuthContext
	 * @see HistogramRes
	 */
	@Override
	public HistogramRes courseHistogram(AuthContext authContext) {
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
		}
		List<Course> courses = courseLearnStatisticsMapper.selectByHeat(courseIds);
		courses = courses.stream().filter(s -> s.getHeat() > 0).collect(Collectors.toList());
		return getCourseHistogramRes(courses);
	}

	/**
	 * 方向直方图
	 *
	 * @param authContext auth 上下文
	 * @return {@code HistogramRes }
	 * @throws
	 * @see AuthContext
	 * @see HistogramRes
	 */
	@Override
	public HistogramRes directionHistogram(AuthContext authContext) {
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
		}
		List<Course> directions = courseLearnStatisticsMapper.sumHeatByDirection(courseIds);
		directions = directions.stream().map(direction -> {
			String directionHeats = direction.getDescription();
			String[] heatLists = directionHeats.split("_");
			int[] heats = Arrays.stream(heatLists).mapToInt(Integer::valueOf).toArray();
			int heat = Arrays.stream(heats).sum();
			direction.setHeat(heat);
			return direction;
		}).collect(Collectors.toList());
		directions = directions.stream().filter(d-> d.getHeat() > 0).collect(Collectors.toList());
		directions = directions.stream().sorted(Comparator.comparing(Course::getHeat).reversed()).limit(5).collect(Collectors.toList());
		return getCourseHistogramRes(directions);
	}

	/**
	 * 获取课程直方图结果
	 *
	 * @param courses 课程
	 * @return {@code HistogramRes }
	 * @throws
	 * @see List<Course>
	 * @see HistogramRes
	 */
	private HistogramRes getCourseHistogramRes(List<Course> courses) {
		List<String> xNames = new ArrayList<>();
		List<Integer> yValues = new ArrayList<>();
		for (Course course : courses) {
			Integer heat = course.getHeat();
			xNames.add(course.getName());
			yValues.add(heat);
		}
		HistogramRes res = new HistogramRes();
		res.setXNames(xNames);
		res.setYValues(yValues);
		return res;

	}

	/**
	 * 课程统计列表
	 *
	 * @param context     上下文
	 * @param page        分页
	 * @param authContext auth 上下文
	 * @return {@code ListRsp<AdminCourseStatisticsRes> }
	 * @throws
	 * @see CourseContext
	 * @see Page
	 * @see AuthContext
	 * @see ListRsp<AdminCourseStatisticsRes>
	 */
	@Override
	public ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page,
			AuthContext authContext) {
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
		}

		IPage<AdminCourseStatisticsRes> adminCourseStatisticsResIPage = courseLearnStatisticsMapper
				.listCourseByPage(page, context, courseIds, false);

		return PageUtil
				.getListResult(adminCourseStatisticsResIPage.convert(this::curseStatisticToBo));
	}

	/**
	 * curse 统计到 bo
	 *
	 * @param adminCourseStatisticsRes 管理课程统计学结果
	 * @return {@code AdminCourseStatisticsRes }
	 * @throws
	 * @see AdminCourseStatisticsRes
	 * @see AdminCourseStatisticsRes
	 */
	private AdminCourseStatisticsRes
			curseStatisticToBo(AdminCourseStatisticsRes adminCourseStatisticsRes) {
		UserInfo userInfo = remoteUserService.getById(adminCourseStatisticsRes.getCreateUser());
		adminCourseStatisticsRes.setCreateUser(null == userInfo ? "" : userInfo.getUserName());
		return adminCourseStatisticsRes;
	}

	/**
	 * 课程统计列表
	 *
	 * @param courseId 课程id
	 * @param page     分页
	 * @param pageReq  分页请求体
	 * @return {@code ListRsp<AdminLessonStatisticsRes> }
	 * @throws
	 * @see String
	 * @see Page
	 * @see LessonStatisticsListReq
	 * @see ListRsp<AdminLessonStatisticsRes>
	 */
	@Override
	public ListRsp<AdminLessonStatisticsRes> LessonStatisticsList(String courseId, Page page,
			LessonStatisticsListReq pageReq) {
		IPage<AdminLessonStatisticsRes> rsp = courseLearnStatisticsMapper.listLessonByPage(page,
				courseId, true, pageReq.getType(), pageReq.getSearch());
		List<String> lessonIds = StreamApiUtils.map(rsp.getRecords(),
				AdminLessonStatisticsRes::getResourceId);
		List<AdminLessonStatisticsRes> lessonPractices = courseLearnStatisticsMapper
				.listLessonPractice(lessonIds);
		Map<String, AdminLessonStatisticsRes> resMap = lessonPractices.stream().collect(
				Collectors.toMap(AdminLessonStatisticsRes::getResourceId, record -> record));
		List<AdminLessonStatisticsRes> res = rsp.getRecords().stream().map(rspRecord -> {

			AdminLessonStatisticsRes adminLessonStatisticsRes = resMap
					.get(rspRecord.getResourceId());
			if (null == adminLessonStatisticsRes) {
				return rspRecord;
			}
			String record = adminLessonStatisticsRes.getRecord();
			String[] records = record.split("_");
			int practiceCount = 0;
			for (String item : records) {
				practiceCount = practiceCount + JSONArray.parseArray(item).size();
			}
			rspRecord.setPracticeCount(practiceCount);
			return rspRecord;
		}).collect(Collectors.toList());
		rsp.setRecords(res);
		//根据完成练习次数排序
		if(page.getOrders() != null && page.getOrders().size() >0 && ((OrderItem)page.getOrders().get(0)).getColumn().equals("practice_count")){
			PageUtil.setPageOrder(page, "practiceCount", ((OrderItem)page.getOrders().get(0)).isAsc());
			return PageUtil.getListResult(rsp.getRecords(), page, rsp.getTotal());
		}
		return PageUtil.getListResult(rsp);
	}

	/**
	 * 课统计学详情
	 *
	 * @param organizationId 组织id
	 * @param lessonId       课id
	 * @param usernameLike   username like
	 * @param page           分页
	 * @param sort           排序
	 * @return {@code ListRsp<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Page
	 * @see String
	 * @see ListRsp<AdminLessonStatisticsDetailRes>
	 */
	@Override
	public ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String organizationId,
																		  String lessonId, String usernameLike, Page page, String sort) {
		List<String> userIds = getUsers(organizationId, usernameLike);

		if (userIds.size() == 0){
			return PageUtil.getListResult(null);
		}
		// 获取学习该课程的所有用户
		IPage<AdminLessonStatisticsDetailRes> learnedUsers = courseLearnStatisticsMapper
				.listLessonLearnedRecord(page, userIds, lessonId);
		List<AdminLessonStatisticsDetailRes> res = learnedUsers.getRecords();
		res = res.stream().map(data -> {
			String record = data.getRecord();
			if(null != record) {
				int practiceCount = JSONArray.parseArray(record).size();
				data.setPracticeCount(practiceCount);
			}
			if (null != data.getReportUpdateTime()){
				data.setIsReport(true);
			}
			String createUser = data.getCreateUser();
			UserInfo userInfo = remoteUserService.getById(createUser);
			data.setCreateUser(userInfo.getUserName());
			data.setName(userInfo.getName());
			data.setUserName(userInfo.getUserName());
			data.setOrganizationName(userInfo.getOrganizationData());
			return data;
		}).collect(Collectors.toList());
		learnedUsers.setRecords(res);
		return PageUtil.getListResult(learnedUsers);
	}

	/**
	 * 获取用户
	 *
	 * @param organizationId 组织id
	 * @param usernameLike   username like
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see String
	 * @see List<String>
	 */
	public List<String> getUsers(String organizationId, String usernameLike) {
		List<String> userIds = new ArrayList<>();
		// 根据用户名pattern + 组织id获取userids
		if (StringUtils.isNotEmpty(organizationId)) {
			if (organizationId.equals("all")) {
				organizationId = "";
			}
			JSONObject userJsons = remoteUserService.getList(null, 0L, null, organizationId);
			List<UserInfo> users = userJsons.getJSONArray("list").toJavaList(UserInfo.class);
			if (StringUtils.isNotEmpty(usernameLike) && StringUtils.isNotBlank(usernameLike)) {
				users = StreamApiUtils.filter(users,
						userInfo -> StringUtils.isBlank(usernameLike)
								|| userInfo.getUserName().contains(usernameLike)
								|| userInfo.getName().contains(usernameLike));
			}
			userIds = StreamApiUtils.map(users, UserInfo::getUserId);
		}
		return userIds;
	}
}

