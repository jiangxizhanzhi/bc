package com.cyberpeace.oj.course.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.oj.course.dao.entity.CourseCategory;
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
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
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
	public HistogramRes courseHistogram() {
		List<Course> courses = courseLearnStatisticsMapper.selectByHeat();
		return getCourseHistogramRes(courses);
	}

	@Override
	public HistogramRes directionHistogram() {
		List<Course> directions = courseLearnStatisticsMapper.sumHeatByDirection();
		directions = directions.stream().map(direction -> {
			String directionHeats = direction.getDescription();
			if (null == directionHeats) {
				return direction;
			}
			String[] heatLists = directionHeats.split("_");
			int[] heats = Arrays.stream(heatLists).mapToInt(Integer::valueOf).toArray();
			int heat = Arrays.stream(heats).sum();
			direction.setHeat(heat);
			return direction;
		}).collect(Collectors.toList());
		directions = directions.stream().sorted(Comparator.comparing(Course::getHeat).reversed()).limit(5).collect(Collectors.toList());
		return getCourseHistogramRes(directions);
	}

	private HistogramRes getCourseHistogramRes(List<Course> courses) {
		List<String> xNames = new ArrayList<>();
		List<Integer> yValues = new ArrayList<>();
		for (Course course : courses) {
			xNames.add(course.getName());
			yValues.add(course.getHeat());
		}
		HistogramRes res = new HistogramRes();
		res.setXNames(xNames);
		res.setYValues(yValues);
		return res;

	}

	@Override
	public ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page,
			AuthContext authContext) {
		List<String> courseIds = null;
		if (null != authContext) {
			courseIds = courseMapper.selectByAuth(authContext);
		}

		IPage<AdminCourseStatisticsRes> adminCourseStatisticsResIPage = courseLearnStatisticsMapper
				.listCourseByPage(page, context, courseIds, false);
		List<AdminCourseStatisticsRes> records = adminCourseStatisticsResIPage.getRecords();
		List<AdminCourseStatisticsRes> realRecords = records.stream()
				.filter(record -> null != remoteUserService.getById(record.getCreateUser()))
				.collect(Collectors.toList());
		adminCourseStatisticsResIPage.setRecords(realRecords);
		adminCourseStatisticsResIPage.setTotal(realRecords.size());
		return PageUtil
				.getListResult(adminCourseStatisticsResIPage.convert(this::curseStatisticToBo));
	}

	private AdminCourseStatisticsRes
			curseStatisticToBo(AdminCourseStatisticsRes adminCourseStatisticsRes) {
		UserInfo userInfo = remoteUserService.getById(adminCourseStatisticsRes.getCreateUser());
		adminCourseStatisticsRes.setCreateUser(userInfo.getUserName());
		return adminCourseStatisticsRes;
	}

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
		return PageUtil.getListResult(rsp);
	}

	@Override
	public ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String organizationId,
			String lessonId, String usernameLike, Page page) {
		List<String> userIds = new ArrayList<>();
		// 根据组织id获取userids
		if (StringUtils.isNotEmpty(organizationId)) {
			if (organizationId.equals("all")) {
				organizationId = "";
			}
			JSONObject userJsons = remoteUserService.getList(0L, 0L, "", organizationId);
			List<UserInfo> users = userJsons.getJSONArray("list").toJavaList(UserInfo.class);
			userIds = StreamApiUtils.map(users, UserInfo::getUserId);
		}

		// 根据用户名pattern获取userids
		if (StringUtils.isNotEmpty(usernameLike)) {
			UserPageContext userPageContext = new UserPageContext();
			userPageContext.setUsername(usernameLike);
			List<UserInfo> lazyUserList = remoteUserService.getFuzzyQueryUserList(userPageContext);
			userIds = StreamApiUtils.map(lazyUserList, UserInfo::getUserId);
		}
		// 获取学习该课程的所有用户
		IPage<AdminLessonStatisticsDetailRes> learnedUsers = courseLearnStatisticsMapper
				.listLessonLearnedRecord(page, lessonId);

		// 获取课后练习完成数据
		List<AdminLessonStatisticsDetailRes> practices = courseLearnStatisticsMapper
				.detailLessonByPage(lessonId, userIds);
		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);

		Map<String, AdminLessonStatisticsDetailRes> expMap = new HashMap<>();
		Map<String, AdminLessonStatisticsDetailRes> practiceMap = practices.stream()
				.collect(Collectors.toMap(AdminLessonStatisticsDetailRes::getCreateUser,
						lessonStatisticsDetailRes -> lessonStatisticsDetailRes));
		// 实验课时 获取实验报告的数据
		if (CourseLesson.LessonType.EXPERIMENT.getCode().equals(courseLesson.getType())) {
			List<AdminLessonStatisticsDetailRes> expReportData = courseLearnStatisticsMapper
					.detailExpLessonByPage(lessonId, userIds);

			expMap = expReportData.stream()
					.collect(Collectors.toMap(AdminLessonStatisticsDetailRes::getCreateUser,
							expLessonStatisticsDetailRes -> expLessonStatisticsDetailRes));
		}
		// 实验课额外获取实验报告数据
		List<AdminLessonStatisticsDetailRes> records = learnedUsers.getRecords();
		for (AdminLessonStatisticsDetailRes res : records) {

			String createUser = res.getCreateUser();
			UserInfo userInfo = remoteUserService.getById(createUser);
			res.setCreateUser(userInfo.getUserName());
			res.setOrganizationName(userInfo.getOrganizationData());
			AdminLessonStatisticsDetailRes practiceRecord = practiceMap.get(createUser);
			if (null != practiceRecord) {
				int practiceCount = JSONArray.parseArray(practiceRecord.getRecord()).size();
				res.setPracticeCount(practiceCount);
			}
			if (CourseLesson.LessonType.EXPERIMENT.getCode().equals(courseLesson.getType())) {
				AdminLessonStatisticsDetailRes currentExpData = expMap.get(createUser);
				if (null != currentExpData) {
					res.setIsReport(true);
					res.setMarkingTime(currentExpData.getMarkingTime());
					res.setScore(currentExpData.getScore());
					res.setReportUpdateTime(currentExpData.getReportUpdateTime());
				}
			}
		}
		learnedUsers.setRecords(records);
		return PageUtil.getListResult(learnedUsers);
	}
}
