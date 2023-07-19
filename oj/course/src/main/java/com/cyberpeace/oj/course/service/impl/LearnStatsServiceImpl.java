package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.OrgInfo;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.LearnStatsMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.practice.entity.Paper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LearnStatsServiceImpl implements LearnStatsService {

	static final int TOP_TEN = 10;

	static final int TOP_FIVE = 5;

	static final int MIN_SCORE = 80; // 获取竞赛-理论赛的百分换算后的最低分数标准

	private static final String USER_ID = "user_id";

	private static final String COUNT = "count";

	@Autowired
	LearnStatsMapper learnStatsMapper;

	@Autowired
	RemoteUserService remoteUserService;

	@Autowired
	RemoteOrganizationService remoteOrganizationService;

	@Autowired
	RemoteAdService remoteAdService;

	@Autowired
	CourseLearnStatisticsService courseLearnStatisticsService;

	ExecutorService es = Executors.newCachedThreadPool();

	@Override
	public ListRsp<UserStatsBo> getUserStats(Page<UserStatsBo> page, String pattern, String organization,
			boolean isPage, String startTimeStr, String endTimeStr) {
		// 校验并格式化起止时间
		Date endTime = new Date();
		Date startTime;
		if (null != endTimeStr && !endTimeStr.isEmpty()) {
			endTime = TimeUtil.toDate2(endTimeStr);
		}
		startTime = getDefaultStartTime(startTimeStr, endTime);
		startTimeStr = TimeUtil.format6(startTime);
		endTimeStr = TimeUtil.format6(endTime);

		// pattern用户名及organization 获取指定查询用户
		List<UserInfo> userInfos;
		OrderItem orderField = page.getOrders().isEmpty() ? new OrderItem()
				: (OrderItem) page.getOrders().get(0);

		if (pattern == null && organization == null) {
			userInfos = remoteUserService.getAllUsers();
		} else {
			userInfos = remoteUserService.getList(null, 0L, pattern, organization)
					.getJSONArray("list").toJavaList(UserInfo.class);
		}
		userInfos = userInfos.stream()
				.filter(userInfo -> userInfo.getName() != null && !userInfo.getName().isEmpty())
				.collect(Collectors.toList());
		if (userInfos.isEmpty()) {
			return new ListRsp<>();
		}

		List<String> userIds = userInfos.stream().map(UserInfo::getUserId)
				.collect(Collectors.toList());

		// 获取用户课时学习情况
		Map<String, UserStatsBo> userStatsMap = learnStatsMapper
				.userStatsSelect(userIds, Note.NoteType.REPORT.getCode(), startTime, endTime)
				.stream()
				.collect(Collectors.toMap(UserStatsBo::getResourceId, userStatsBo -> userStatsBo));

		// 获取竞赛的理论赛参加次数
		if (null == organization && null == pattern) {
			userIds = new ArrayList<>();
		}
		List<JSONObject> examUserCount = remoteAdService.competitionListWithMinScore(startTimeStr,
				endTimeStr, userIds, MIN_SCORE);
		Map<String, Integer> examCount = getUserWithCount(examUserCount);

		List<UserStatsBo> userStatsBos = Lists.newArrayList();
		userInfos.forEach(item -> {
			UserStatsBo userStatsBo = new UserStatsBo();

			if (userStatsMap.get(item.getUserId()) != null) {
				BeanUtils.copyProperties(userStatsMap.get(item.getUserId()), userStatsBo);
			}
			userStatsBo.setResourceId(item.getUserId());
			userStatsBo.setUsername(item.getUserName());
			userStatsBo.setName(item.getName());
			userStatsBo.setOrganizationData(item.getOrganizationData());
			userStatsBo.setRoles(item.getRoles());
			userStatsBo.setAvatar(item.getAvatarUrl());
			Integer finishExamCount = examCount.get(item.getUserId());
			userStatsBo.setFinishExamCount(finishExamCount == null ? 0 : finishExamCount);
			userStatsBos.add(userStatsBo);
		});
		// 用于全量导出
		if (!isPage) {
			ListRsp<UserStatsBo> res = new ListRsp<>();
			res.setList(userStatsBos);
			return res;
		}
		return pagedData(page, userInfos.size(), orderField, userStatsBos);
	}

	/**
	 * 分页
	 * @param page 分页对象
	 * @param total 总数
	 * @param orderField 排序字段
	 * @param userStatsBos 带分页数据
	 * @return 分页后的数据
	 */
	private <T> ListRsp<T> pagedData(Page<T> page, Integer total, OrderItem orderField,
			List<T> userStatsBos) {
		ListRsp<T> responseData = new ListRsp<>();

		if (orderField.getColumn() != null) {
			LocalStringUtil
					.getMatchStr(orderField.getColumn(), PatternConstant.UNDER_LINE_LOWERCASE)
					.forEach(key -> orderField.setColumn(
							orderField.getColumn().replace(key, key.toUpperCase().substring(1))));

			Comparator<T> userStatsBoComparator = Comparator.comparing(item -> Float
					.valueOf(ObjUtil.getFieldValueByName(orderField.getColumn(), item).toString()));
			if (!orderField.isAsc()) {
				userStatsBoComparator = userStatsBoComparator.reversed();
			}

			userStatsBos.sort(userStatsBoComparator);
		}
		responseData.setList(userStatsBos.stream().skip((page.getCurrent() - 1) * page.getSize())
				.limit(page.getSize()).collect(Collectors.toList()));

		responseData.setTotal(total);
		responseData.setCurrentPage(page.getCurrent());
		responseData.setLastPage(MathUtils.ceil(responseData.getTotal() * 1.0 / page.getSize()));
		return responseData;
	}

	@Override
	public UserStatsRankBo getUserLearnRank() {
		int limit = TOP_FIVE;
		long startTime = System.currentTimeMillis();
		List<UserInfo> userInfos = remoteUserService.getAllUsers();
		List<String> userIds = userInfos.stream().map(UserInfo::getUserId)
				.collect(Collectors.toList());
		Map<String, String> usernameMap = userInfos.stream()
				.collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getName));
		if (log.isDebugEnabled()) {
			log.debug("getAllUser cost =" + (System.currentTimeMillis() - startTime));
		}
		UserStatsRankBo userStatsRankBo = new UserStatsRankBo();

//		String traceId = TraceIdContext.getTraceId();
		String traceId =ThreadContext.get("logId");
		CountDownLatch countDownLatch = new CountDownLatch(3);

		// 课时学习排行
		es.submit(() -> {
			long st = System.currentTimeMillis();
			userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
			userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
			learnStatsMapper.lessonRecordRank(userIds, limit).forEach(item -> {
				userStatsRankBo.getLessonRecordUser()
						.add(usernameMap.get(item.getString(USER_ID)));
				userStatsRankBo.getLessonRecordCount().add(item.getInteger(COUNT));
			});
			if (log.isDebugEnabled()) {
				log.debug("{}|lessonRecordRank cost ={}", traceId,
						(System.currentTimeMillis() - st));
			}
			try {
				countDownLatch.countDown();
			} catch (Exception e) {}
		});

		// 完成报告排行
		es.submit(() -> {
			long st = System.currentTimeMillis();
			userStatsRankBo.setFinishReportUser(Lists.newArrayList());
			userStatsRankBo.setFinishReportCount(Lists.newArrayList());
			learnStatsMapper.reportFinishRank(userIds, Note.NoteType.REPORT.getCode(), limit)
					.forEach(item -> {
						userStatsRankBo.getFinishReportUser()
								.add(usernameMap.get(item.getString(USER_ID)));
						userStatsRankBo.getFinishReportCount().add(item.getInteger(COUNT));
					});
			if (log.isDebugEnabled()) {
				log.debug("{}|reportFinishRank cost ={}", traceId,
						(System.currentTimeMillis() - st));
			}
			try {
				countDownLatch.countDown();
			} catch (Exception e) {}
		});

		// 完成课后练习排行
		es.submit(() -> {
			long st = System.currentTimeMillis();
			userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
			userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());
			learnStatsMapper.practiceFinishRank(userIds, limit).forEach(item -> {
				userStatsRankBo.getFinishPracticeUser()
						.add(usernameMap.get(item.getString(USER_ID)));
				userStatsRankBo.getFinishPracticeCount().add(item.getInteger(COUNT));
			});
			if (log.isDebugEnabled()) {
				log.debug("{}|practiceFinishRank cost ={}", traceId,
						(System.currentTimeMillis() - st));
			}
			try {
				countDownLatch.countDown();
			} catch (Exception e) {}
		});

		// 获取竞赛的考试排行
		startTime = System.currentTimeMillis();
		userStatsRankBo.setAttendExamUser(Lists.newArrayList());
		userStatsRankBo.setAttendExamCount(Lists.newArrayList());
		Date endTime = new Date();
		List<JSONObject> examIntegrals = remoteAdService.competitionListWithMinScore(
				TimeUtil.format6(DateUtils.addDays(endTime, -CommonConstant.TWENTY_FOUR_MONTHS)),
				TimeUtil.format6(endTime), null, MIN_SCORE);
		Map<String, Integer> integralExam = getUserWithCount(examIntegrals);
		ArrayList<Map.Entry<String, Integer>> entries = getTopCount(integralExam, TOP_FIVE);
		entries.forEach(item -> {
			if (usernameMap.get(item.getKey()) != null) {
				userStatsRankBo.getAttendExamUser().add(usernameMap.get(item.getKey()));
				userStatsRankBo.getAttendExamCount().add(item.getValue());
			} else {
				log.info("user is not exist. userId = [{}]", item.getKey());

			}

		});
		if (log.isDebugEnabled()) {
			log.debug("{}|competitionListWithMinScore cost ={}", traceId,
					(System.currentTimeMillis() - startTime));
		}

		try {
			countDownLatch.await(300, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("task interrupted", e);
		}

		return userStatsRankBo;
	}

	@Override
	public PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId) {
		PersonalStatsDetail4CountBo result = new PersonalStatsDetail4CountBo();

		// 课程总数
		JSONObject courseNumStats = learnStatsMapper.courseNumStats(
				CourseLesson.LessonType.THEORY.getCode(),
				CourseLesson.LessonType.EXPERIMENT.getCode());
		result.setCourseTotalCount(courseNumStats.getInteger("course_count"));
		result.setTheoryCourseTotalCount(courseNumStats.getInteger("theory_count"));
		result.setExpCourseTotalCount(courseNumStats.getInteger("exp_count"));

		// 已学习课程数量
		JSONObject learnNumStats = learnStatsMapper.learnNumStats(resourceId,
				CourseLesson.LessonType.THEORY.getCode(),
				CourseLesson.LessonType.EXPERIMENT.getCode());
		result.setFinishCoursesCount(learnNumStats.getInteger("finish_course_count"));
		result.setFinishTheoryCount(learnNumStats.getInteger("finish_theory_count"));
		result.setFinishExpCount(learnNumStats.getInteger("finish_exp_count"));

		// 课后练习总数、已完成数量
		JSONObject practiceNumStats = learnStatsMapper.practiceNumStats(resourceId,
				Paper.PaperType.LESSON.getCode());
		result.setAfterClassPracticeTotalCount(practiceNumStats.getInteger("practice_count"));
		result.setAfterClassPractice(practiceNumStats.getInteger("finish_practice_count"));

		return result;
	}

	@Override
	public ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page<PersonalStatsDetail4CourseBo> page,
			String resourceId) {
		return PageUtil.getListResult(learnStatsMapper.learnCourseStats(page, resourceId,
				CourseLesson.LessonType.THEORY.getCode(),
				CourseLesson.LessonType.EXPERIMENT.getCode(), Note.NoteType.REPORT.getCode()));
	}

	@Override
	public HistogramRes getClassIntegralTopTen(String rankingType, String orgId) {
		List<UserInfo> userInfos = remoteUserService.getAllUsers();
		List<String> userIds = userInfos.stream().map(UserInfo::getUserId)
				.collect(Collectors.toList());

		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -30);
		String startStr = TimeUtil.format6(startTime);
		String endStr = TimeUtil.format6(endTime);

		// 课后练习积分情况
		List<JSONObject> afterClassIntegrals = learnStatsMapper.afterClassIntegral(userIds,
				startTime, endTime);
		Map<String, Integer> integralAfterClass = getUserWithIntegral(afterClassIntegrals);

		// 课时学习积分情况
		List<JSONObject> lessonIntegrals = learnStatsMapper.lessonIntegral(userIds, startTime,
				endTime);
		Map<String, Integer> integralLesson = getUserWithIntegral(lessonIntegrals);

		// 获取竞赛的理论赛的积分数据
		List<JSONObject> examIntegrals = remoteAdService.competitionListWithMinScore(startStr,
				endStr, null, MIN_SCORE);
		Map<String, Integer> integralExam = getUserWithCount(examIntegrals);

		List<String> xNames = new ArrayList<>();
		List<Integer> yValues = new ArrayList<>();
		Map<String, Integer> result = new HashMap<>();
		String integralString ="integral";
		// 班级积分合并
		if (rankingType.equals("org")) {
			Map<String, String> childrenOrg = collectChildrenOrg(null);
			Map<String, List<UserInfo>> orgUsers = userInfos.stream()
					.filter(item -> StringUtils.isNotBlank(item.getOrganizationId()))
					.collect(Collectors.groupingBy(UserInfo::getOrganizationId));
			for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
				String orgKey = org.getKey();
				int integral = 0;
				if (orgUsers.containsKey(orgKey)) {
					List<UserInfo> orgInfos = orgUsers.get(orgKey);
					integral += afterClassIntegrals.stream()
							.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
									.collect(Collectors.toList())
									.contains(tmp.getString(USER_ID)))
							.mapToInt(s -> Integer.parseInt(s.getString(integralString))).sum();
					integral += lessonIntegrals.stream()
							.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
									.collect(Collectors.toList())
									.contains(tmp.getString(USER_ID)))
							.mapToInt(s -> Integer.parseInt(s.getString(integralString))).sum();
					integral += examIntegrals.stream()
							.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
									.collect(Collectors.toList())
									.contains(tmp.getString(USER_ID)))
							.mapToInt(s -> Integer.parseInt(s.getString(COUNT))).sum()
							* CommonConstant.TEN;
				}
				if (integral > 0) {
					result.put(org.getValue(), integral);
				}
			}

			// 个人积分合并
		} else if (rankingType.equals("person")) {
			userInfos.forEach(user -> {
				String userId = user.getUserId();
				int integral = 0;
				if (integralAfterClass.containsKey(userId)) {
					integral += integralAfterClass.get(userId);
				}
				if (integralLesson.containsKey(userId)) {
					integral += integralLesson.get(userId);
				}
				if (integralExam.containsKey(userId)) {
					integral += integralExam.get(userId) * CommonConstant.TEN;
				}
				if (integral > 0) {
					result.put(user.getName(), integral);
				}
			});
		}
		ArrayList<Map.Entry<String, Integer>> entries = getTopCount(result, TOP_TEN);
		for (Map.Entry<String, Integer> tmp : entries) {
			if (tmp.getValue() != 0) {
				xNames.add(tmp.getKey());
				yValues.add(tmp.getValue());
			}
		}
		HistogramRes res = new HistogramRes();
		res.setXNames(xNames);
		res.setYValues(yValues);
		return res;
	}

	private ArrayList<Map.Entry<String, Integer>> getTopCount(Map<String, Integer> result,
			Integer topCount) {
		List<Map.Entry<String, Integer>> ls = new ArrayList<>(result.entrySet());
		ls.sort((o2, o1) -> o1.getValue() - o2.getValue());
		// 获取Top10
		ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(ls);
		if (ls.size() > topCount) {
			entries = new ArrayList<>(ls.subList(0, topCount));
		}
		return entries;
	}

	@Override
	public UserStatsRankBo getOrgLearnRank(HttpServletRequest request) {
		UserStatsRankBo userStatsRankBo = new UserStatsRankBo();

		List<UserInfo> userInfos = remoteUserService.getAllUsers();
		Map<String, String> childrenOrg = collectChildrenOrg(null);
		Map<String, List<UserInfo>> orgUsers = userInfos.stream()
				.filter(item -> StringUtils.isNotBlank(item.getOrganizationId()))
				.collect(Collectors.groupingBy(UserInfo::getOrganizationId));
		List<String> userIds = userInfos.stream().map(UserInfo::getUserId)
				.collect(Collectors.toList());

		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -CommonConstant.TWENTY_FOUR_MONTHS);
		String startStr = TimeUtil.format6(startTime);
		String endStr = TimeUtil.format6(endTime);

		// 课时学习全量排行
		userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
		userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
		List<JSONObject> lessons = learnStatsMapper.lessonRecordRank(userIds, null);

		// 完成报告全量排行
		userStatsRankBo.setFinishReportUser(Lists.newArrayList());
		userStatsRankBo.setFinishReportCount(Lists.newArrayList());
		List<JSONObject> reports = learnStatsMapper.reportFinishRank(userIds,
				Note.NoteType.REPORT.getCode(), null);

		// 完成课后练习全量排行
		userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
		userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());
		List<JSONObject> afterClassPractices = learnStatsMapper.practiceFinishRank(userIds, null);

		userStatsRankBo.setAttendExamUser(Lists.newArrayList());
		userStatsRankBo.setAttendExamCount(Lists.newArrayList());

		Map<String, Integer> lessonMap = new HashMap<>();
		Map<String, Integer> reportMap = new HashMap<>();
		Map<String, Integer> afterClassMap = new HashMap<>();
		Map<String, Integer> examMap = new HashMap<>();

		// 获取竞赛的理论赛参加次数排名
		List<JSONObject> examUserCount = remoteAdService.competitionListWithMinScore(startStr,
				endStr, null, MIN_SCORE);

		for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
			String orgId = org.getKey();
			int orgLessons = 0;
			int orgReports = 0;
			int orgPractices = 0;
			int orgExams = 0;
			if (orgUsers.containsKey(orgId)) {
				List<UserInfo> orgInfos = orgUsers.get(orgId);
				orgLessons = getSum(lessons, orgInfos);
				orgReports = getSum(reports, orgInfos);
				orgPractices = getSum(afterClassPractices, orgInfos);
				orgExams = getSum(examUserCount, orgInfos);
			}
			if (orgLessons > 0) {
				lessonMap.put(org.getValue(), orgLessons);
			}
			if (orgReports > 0) {
				reportMap.put(org.getValue(), orgReports);
			}
			if (orgPractices > 0) {
				afterClassMap.put(org.getValue(), orgPractices);
			}
			if (orgExams > 0) {
				examMap.put(org.getValue(), orgExams);
			}
		}
		ArrayList<Map.Entry<String, Integer>> lessonEntries = getTopCount(lessonMap, TOP_FIVE);
		ArrayList<Map.Entry<String, Integer>> reportEntries = getTopCount(reportMap, TOP_FIVE);
		ArrayList<Map.Entry<String, Integer>> afterClassEntries = getTopCount(afterClassMap,
				TOP_FIVE);
		ArrayList<Map.Entry<String, Integer>> examEntries = getTopCount(examMap, TOP_FIVE);
		lessonEntries.forEach(tmpLesson -> {
			userStatsRankBo.getLessonRecordUser().add(tmpLesson.getKey());
			userStatsRankBo.getLessonRecordCount().add(tmpLesson.getValue());
		});
		reportEntries.forEach(tmpReport -> {
			userStatsRankBo.getFinishReportUser().add(tmpReport.getKey());
			userStatsRankBo.getFinishReportCount().add(tmpReport.getValue());
		});
		afterClassEntries.forEach(tmpAfter -> {
			userStatsRankBo.getFinishPracticeUser().add(tmpAfter.getKey());
			userStatsRankBo.getFinishPracticeCount().add(tmpAfter.getValue());
		});
		examEntries.forEach(tmpExam -> {
			userStatsRankBo.getAttendExamUser().add(tmpExam.getKey());
			userStatsRankBo.getAttendExamCount().add(tmpExam.getValue());
		});
		return userStatsRankBo;
	}

	private int getSum(List<JSONObject> lessons, List<UserInfo> orgInfos) {
		return lessons.stream()
				.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
						.collect(Collectors.toList()).contains(tmp.getString(USER_ID)))
				.mapToInt(s -> Integer.parseInt(s.getString(COUNT))).sum();
	}

	private Map<String, String> collectChildrenOrg(String org) {
		String orgId = null;
		if (null != org && !org.isEmpty()) {
			orgId = org;
		}
		List<OrgInfo> orgInfos = remoteOrganizationService.classList(orgId)
				.toJavaList(OrgInfo.class);

		Map<String, String> childrenOrg = new HashMap<>();
		getAllChildrenOrg(orgInfos, childrenOrg);
		return childrenOrg;
	}

	private Map<String, Integer> getUserWithIntegral(List<JSONObject> afterClassIntegrals) {
		return StreamApiUtils.toMap(afterClassIntegrals,
				afterClassIntegral -> afterClassIntegral.getString(USER_ID),
				afterClassIntegral -> Integer.parseInt(afterClassIntegral.getString("integral")));
	}

	private Map<String, Integer> getUserWithCount(List<JSONObject> examIntegrals) {
		return StreamApiUtils.toMap(examIntegrals,
				examIntegral -> examIntegral.getString(USER_ID),
				examIntegral -> Integer.parseInt(examIntegral.getString(COUNT)));
	}

	private void getAllChildrenOrg(List<OrgInfo> orgInfos, Map<String, String> childrenOrg) {

		for (OrgInfo org : orgInfos) {
			List<OrgInfo> children = org.getChildren();
			if (children.isEmpty()) {
				childrenOrg.put(org.getId(), org.getPath());
			} else {
				getAllChildrenOrg(children, childrenOrg);
			}
		}
	}

	@Override
	public ListRsp<OrgStatsBo> getOrgStatsData(Page<OrgStatsBo> page, String pattern, String organization,
			String startTimeStr, String endTimeStr, boolean isPage) {
		// 获取并校验时间参数有起止时间时校验格式 否则默认取month date_type对应两个类型 1.month取当月起往前两年的时间区间
		// 2.day取当日起往前30天的时间区间
		Date endTime = new Date();
		Date startTime;

		if (null != endTimeStr && !endTimeStr.isEmpty())
			endTime = TimeUtil.toDate2(endTimeStr);
		startTime = getDefaultStartTime(startTimeStr, endTime);
		if (endTime.compareTo(startTime) < 0) {
			throw new InvalidParamException("incorrect time params！");
		}
		// 获取组织、人名模糊查询得到的用户
		if (organization == null || organization.isEmpty()) {
			organization = null;
		}
		List<UserInfo> userInfos = remoteUserService.getAllUsers();
		List<String> userIds = userInfos.stream().map(UserInfo::getUserId)
				.collect(Collectors.toList());

		if (userIds.isEmpty()) {
			return PageUtil.getListResult(null);
		}
		// 获取所有的末梢组织
		Map<String, String> childrenOrg = collectChildrenOrg(organization);
		Map<String, List<UserInfo>> orgUsers = userInfos.stream()
				.filter(item -> StringUtils.isNotBlank(item.getOrganizationId()))
				.collect(Collectors.groupingBy(UserInfo::getOrganizationId));

		List<OrgStatsBo> result = Lists.newArrayList();
		List<UserStatsBo> userStatsBos = learnStatsMapper.userStatsSelect(userIds,
				Note.NoteType.REPORT.getCode(), startTime, endTime);
		List<JSONObject> examUserCount = remoteAdService.competitionListWithMinScore(
				TimeUtil.format6(startTime), TimeUtil.format6(endTime), null, MIN_SCORE);
		for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
			String orgId = org.getKey();
			List<UserStatsBo> collect = new ArrayList<>();
			int orgUserCount = 0;
			int examCount = 0;
			if (orgUsers.containsKey(orgId)) {
				List<UserInfo> orgInfos = orgUsers.get(orgId);
				collect = userStatsBos.stream()
						.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
								.collect(Collectors.toList()).contains(tmp.getResourceId()))
						.collect(Collectors.toList());
				examCount = examUserCount.stream()
						.filter(tmp -> orgInfos.stream().map(UserInfo::getUserId)
								.collect(Collectors.toList()).contains(tmp.get(USER_ID)))
						.mapToInt(s -> Integer.parseInt(s.getString(COUNT))).sum();
				orgUserCount = (int) userInfos.stream()
						.filter(s -> StringUtils.isNotBlank(s.getOrganizationId())
								&& s.getOrganizationId().equals(orgId))
						.count();
			}
			result.add(generateObj(collect, org.getValue(), orgUserCount, examCount));

		}
		OrderItem orderField = page.getOrders().isEmpty() ? new OrderItem()
				: (OrderItem) page.getOrders().get(0);
		if (!isPage) {
			ListRsp<OrgStatsBo> res = new ListRsp<>();
			res.setList(result);
			return res;
		}
		return pagedData(page, result.size(), orderField, result);
	}

	private OrgStatsBo generateObj(List<UserStatsBo> collect, String orgName, Integer orgUserCount,
			Integer examCount) {
		OrgStatsBo res = new OrgStatsBo();
		res.setOrganizationData(orgName);
		res.setOrgUserCount(orgUserCount);
		res.setFinishPracticeCount(
				collect.stream().mapToInt(UserStatsBo::getFinishPracticeCount).sum());
		res.setFinishReportCount(
				collect.stream().mapToInt(UserStatsBo::getFinishReportCount).sum());
		res.setLessonRecordCount(
				collect.stream().mapToInt(UserStatsBo::getLessonRecordCount).sum());
		// 竞赛理论赛的时间过滤下的数据
		res.setFinishExamCount(examCount);
		// 设置实验平均分
		int sumReportCount = 0;
		float sumReport = 0;
		for (UserStatsBo userStatsBo : collect) {
			sumReportCount += userStatsBo.getFinishReportCount();
			sumReport += userStatsBo.getFinishReportCount() * userStatsBo.getReportAverage();
		}
		if (sumReportCount != 0) {
			res.setReportAverage(sumReport / sumReportCount);
		} else {
			res.setReportAverage(0f);
		}

		return res;

	}

	private Date getDefaultStartTime(String startTimeStr, Date endTime) {

		if (null != startTimeStr && !startTimeStr.isEmpty()) {
			return TimeUtil.toDate2(startTimeStr);
		}
		return DateUtils.addDays(endTime, -CommonConstant.THIRTY);

	}
}
