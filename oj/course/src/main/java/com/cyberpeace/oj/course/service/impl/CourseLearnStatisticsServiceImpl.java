package com.cyberpeace.oj.course.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
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
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import com.cyberpeace.oj.course.dao.mapper.CourseLearnStatisticsMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;

@Service
public class CourseLearnStatisticsServiceImpl implements CourseLearnStatisticsService {

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private CourseLearnStatisticsMapper courseLearnStatisticsMapper;

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

	@Override
	public ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page<AdminCourseStatisticsRes> page,
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

	private AdminCourseStatisticsRes
			curseStatisticToBo(AdminCourseStatisticsRes adminCourseStatisticsRes) {
		UserInfo userInfo = remoteUserService.getById(adminCourseStatisticsRes.getCreateUser());
		adminCourseStatisticsRes.setCreateUser(null == userInfo ? "" : userInfo.getUserName());
		return adminCourseStatisticsRes;
	}

	@Override
	public ListRsp<AdminLessonStatisticsRes> lessonStatisticsList(String courseId,
																  Page<AdminLessonStatisticsRes> page, LessonStatisticsListReq pageReq) {
		IPage<AdminLessonStatisticsRes> rsp = courseLearnStatisticsMapper.listLessonByPage(page,
				courseId, true, pageReq.getType(), pageReq.getSearch());
		List<String> lessonIds = StreamApiUtils.map(rsp.getRecords(),
				AdminLessonStatisticsRes::getResourceId);
		List<LessonPaperEvaluate> recordList = courseLearnStatisticsMapper
				.listLessonPractice(lessonIds);
		Map<String, List<LessonPaperEvaluate>> resMap = StreamApiUtils.groupBy(recordList,
				LessonPaperEvaluate::getLessonId);
		List<AdminLessonStatisticsRes> res = rsp.getRecords().stream().map(rspRecord -> {

			List<LessonPaperEvaluate> evaluateList = resMap.get(rspRecord.getResourceId());

			if (null == evaluateList) {
				return rspRecord;
			}
			int practiceCount = 0;
			for (LessonPaperEvaluate evaluate : evaluateList) {
				String recordObject = evaluate.getRecord();
				practiceCount = practiceCount + JSON.parseArray(recordObject).size();
			}
			rspRecord.setPracticeCount(practiceCount);
			return rspRecord;
		}).collect(Collectors.toList());
		rsp.setRecords(res);
		// 根据完成练习次数排序
		if (page.getOrders() != null && !page.getOrders().isEmpty()
				&& (page.getOrders().get(0)).getColumn().equals("practice_count")) {
			PageUtil.setPageOrder(page, "practiceCount",
					(page.getOrders().get(0)).isAsc());
			return PageUtil.getListResult(rsp.getRecords(), page, rsp.getTotal());
		}
		return PageUtil.getListResult(rsp);
	}

	@Override
	public ListRsp<AdminLessonStatisticsDetailRes> lessonStatisticsDetail(String organizationId,
																		  String lessonId, String usernameLike, Page<AdminLessonStatisticsDetailRes> page, String sort) {
		List<String> userIds = getUsers(organizationId, usernameLike);

		if (userIds.isEmpty()){
			return PageUtil.getListResult(null);
		}
		// 获取学习该课程的所有用户
		IPage<AdminLessonStatisticsDetailRes> learnedUsers = courseLearnStatisticsMapper
				.listLessonLearnedRecord(page, userIds, lessonId);
		List<AdminLessonStatisticsDetailRes> res = learnedUsers.getRecords();
		res = res.stream().map(data -> {
			String record = data.getRecord();
			if(null != record) {
				int practiceCount = JSON.parseArray(record).size();
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
