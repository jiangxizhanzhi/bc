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
import org.slf4j.impl.TraceIdContext;
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

/**
 * 学习统计Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see LearnStatsService
 * @since 1.0.0
 */
@Service
@Slf4j
public class LearnStatsServiceImpl implements LearnStatsService {

	/**
	 * 十大
	 */
	final int topTen = 10;

	/**
	 * 前五名
	 */
	final int topFive = 5;

	/**
	 * 最小得分
	 */
	final int minScore = 80; // 获取竞赛-理论赛的百分换算后的最低分数标准

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private static final String USER_ID = "user_id";

	/**
	 * 计数
	 *
	 * @see String
	 */
	private static final String COUNT = "count";

	/**
	 * 学习统计Mapper
	 *
	 * @see LearnStatsMapper
	 */
	@Autowired
	LearnStatsMapper learnStatsMapper;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	RemoteUserService remoteUserService;

	/**
	 * 远程组织Service
	 *
	 * @see RemoteOrganizationService
	 */
	@Autowired
	RemoteOrganizationService remoteOrganizationService;

	/**
	 * 远程竞赛Service
	 *
	 * @see RemoteAdService
	 */
	@Autowired
	RemoteAdService remoteAdService;

	/**
	 * 课程学习统计学Service
	 *
	 * @see CourseLearnStatisticsService
	 */
	@Autowired
	CourseLearnStatisticsService courseLearnStatisticsService;

	/**
	 * 地震魔
	 *
	 * @see ExecutorService
	 */
	ExecutorService es = Executors.newCachedThreadPool();

	/**
	 * 获取用户统计
	 *
	 * @param page         分页
	 * @param pattern      模式
	 * @param organization 组织
	 * @param isPage       是否分页
	 * @param startTimeStr 启动时间str
	 * @param endTimeStr   结束时间 STR
	 * @return {@code ListRsp<UserStatsBo> }
	 * @throws
	 * @see Page<UserStatsBo>
	 * @see String
	 * @see String
	 * @see boolean
	 * @see String
	 * @see String
	 * @see ListRsp<UserStatsBo>
	 */
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
				endTimeStr, userIds, minScore);
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
	 *
	 * @param page         分页对象
	 * @param total        总数
	 * @param orderField   排序字段
	 * @param userStatsBos 带分页数据
	 * @return 分页后的数据
	 * @throws
	 * @see Page<T>
	 * @see Integer
	 * @see OrderItem
	 * @see List<T>
	 * @see ListRsp<T>
	 */
	private <T> ListRsp<T> pagedData(Page<T> page, Integer total, OrderItem orderField,
			List<T> userStatsBos) {
		ListRsp<T> responseData = new ListRsp<T>();

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

	/**
	 * 获取用户学习排
	 *
	 * @return {@code UserStatsRankBo }
	 * @throws
	 * @see UserStatsRankBo
	 */
	@Override
	public UserStatsRankBo getUserLearnRank() {
		int limit = topFive;
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

		String traceId = TraceIdContext.getTraceId();

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
			} catch (Exception e) {
				log.warn("{}|countDownLatch error", traceId);
			}
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
			} catch (Exception e) {
				log.warn("{}|countDownLatch error", traceId);
			}
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
			} catch (Exception e) {
				log.warn("{}|countDownLatch error", traceId);
			}
		});

		// 获取竞赛的考试排行
		startTime = System.currentTimeMillis();
		userStatsRankBo.setAttendExamUser(Lists.newArrayList());
		userStatsRankBo.setAttendExamCount(Lists.newArrayList());
		Date endTime = new Date();
		List<JSONObject> examIntegrals = remoteAdService.competitionListWithMinScore(
				TimeUtil.format6(DateUtils.addDays(endTime, -CommonConstant.TWENTY_FOUR_MONTHS)),
				TimeUtil.format6(endTime), null, minScore);
		Map<String, Integer> integralExam = getUserWithCount(examIntegrals);
		ArrayList<Map.Entry<String, Integer>> entries = getTopCount(integralExam, topFive);
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
			if(!countDownLatch.await(300, TimeUnit.SECONDS)) {
				log.error("countDownLatch task timeout");
			}
		} catch (InterruptedException e) {
			log.error("task interrupted", e);
			Thread.currentThread().interrupt();
		}

		return userStatsRankBo;
	}

	/**
	 * 获取个人统计详情4计数
	 *
	 * @param resourceId 资源id
	 * @return {@code PersonalStatsDetail4CountBo }
	 * @throws
	 * @see String
	 * @see PersonalStatsDetail4CountBo
	 */
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

	/**
	 * 获取个人统计详情4课程
	 *
	 * @param page       分页
	 * @param resourceId 资源id
	 * @return {@code ListRsp<PersonalStatsDetail4CourseBo> }
	 * @throws
	 * @see Page<PersonalStatsDetail4CourseBo>
	 * @see String
	 * @see ListRsp<PersonalStatsDetail4CourseBo>
	 */
	@Override
	public ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page<PersonalStatsDetail4CourseBo> page,
			String resourceId) {
		return PageUtil.getListResult(learnStatsMapper.learnCourseStats(page, resourceId,
				CourseLesson.LessonType.THEORY.getCode(),
				CourseLesson.LessonType.EXPERIMENT.getCode(), Note.NoteType.REPORT.getCode()));
	}

	/**
	 * 获取类完整返回页首十
	 *
	 * @param ranking_type 排名类型
	 * @param orgId        组织id
	 * @return {@code HistogramRes }
	 * @throws
	 * @see String
	 * @see String
	 * @see HistogramRes
	 */
	@Override
	public HistogramRes getClassIntegralTopTen(String ranking_type, String orgId) {
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
				endStr, null, minScore);
		Map<String, Integer> integralExam = getUserWithCount(examIntegrals);

		List<String> xNames = new ArrayList<>();
		List<Integer> yValues = new ArrayList<>();
		Map<String, Integer> result = new HashMap<>();
		String integralString ="integral";
		// 班级积分合并
		if (ranking_type.equals("org")) {
			Map<String, String> childrenOrg = collectChildrenOrg(null);
			Map<String, List<UserInfo>> org_users = userInfos.stream()
					.filter(item -> StringUtils.isNotBlank(item.getOrganizationId()))
					.collect(Collectors.groupingBy(UserInfo::getOrganizationId));
			for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
				String org_id = org.getKey();
				int integral = 0;
				if (org_users.containsKey(org_id)) {
					List<UserInfo> org_infos = org_users.get(org_id);
					integral += afterClassIntegrals.stream()
							.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
									.collect(Collectors.toList())
									.contains(tmp.getString(USER_ID)))
							.mapToInt(s -> Integer.parseInt(s.getString(integralString))).sum();
					integral += lessonIntegrals.stream()
							.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
									.collect(Collectors.toList())
									.contains(tmp.getString(USER_ID)))
							.mapToInt(s -> Integer.parseInt(s.getString(integralString))).sum();
					integral += examIntegrals.stream()
							.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
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
		} else if (ranking_type.equals("person")) {
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
		ArrayList<Map.Entry<String, Integer>> entries = getTopCount(result, topTen);
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

	/**
	 * 获取返回页首计数
	 *
	 * @param result   结果
	 * @param topCount 返回页首计数
	 * @return {@code ArrayList<Map.Entry<String, Integer>> }
	 * @throws
	 * @see Map<String, Integer>
	 * @see Integer
	 * @see ArrayList<Map.Entry<String, Integer>>
	 */
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

	/**
	 * 获取组织学习排
	 *
	 * @param request 请求
	 * @return {@code UserStatsRankBo }
	 * @throws
	 * @see HttpServletRequest
	 * @see UserStatsRankBo
	 */
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
				endStr, null, minScore);

		for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
			String org_id = org.getKey();
			int org_lessons = 0;
			int org_reports = 0;
			int org_practices = 0;
			int org_exams = 0;
			if (orgUsers.containsKey(org_id)) {
				List<UserInfo> org_infos = orgUsers.get(org_id);
				org_lessons = getSum(lessons, org_infos);
				org_reports = getSum(reports, org_infos);
				org_practices = getSum(afterClassPractices, org_infos);
				org_exams = getSum(examUserCount, org_infos);
			}
			if (org_lessons > 0) {
				lessonMap.put(org.getValue(), org_lessons);
			}
			if (org_reports > 0) {
				reportMap.put(org.getValue(), org_reports);
			}
			if (org_practices > 0) {
				afterClassMap.put(org.getValue(), org_practices);
			}
			if (org_exams > 0) {
				examMap.put(org.getValue(), org_exams);
			}
		}
		ArrayList<Map.Entry<String, Integer>> lessonEntries = getTopCount(lessonMap, topFive);
		ArrayList<Map.Entry<String, Integer>> reportEntries = getTopCount(reportMap, topFive);
		ArrayList<Map.Entry<String, Integer>> afterClassEntries = getTopCount(afterClassMap,
				topFive);
		ArrayList<Map.Entry<String, Integer>> examEntries = getTopCount(examMap, topFive);
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

	/**
	 * 获取和
	 *
	 * @param lessons   课
	 * @param org_infos 组织信息
	 * @return int
	 * @throws
	 * @see List<JSONObject>
	 * @see List<UserInfo>
	 * @see int
	 */
	private int getSum(List<JSONObject> lessons, List<UserInfo> org_infos) {
		return lessons.stream()
				.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
						.collect(Collectors.toList()).contains(tmp.getString(USER_ID)))
				.mapToInt(s -> Integer.parseInt(s.getString(COUNT))).sum();
	}

	/**
	 * 收集子级组织
	 *
	 * @param org 组织
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see String
	 * @see Map<String, String>
	 */
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

	/**
	 * 获取用户跟完整
	 *
	 * @param afterClassIntegrals 类积分后
	 * @return {@code Map<String, Integer> }
	 * @throws
	 * @see List<JSONObject>
	 * @see Map<String, Integer>
	 */
	private Map<String, Integer> getUserWithIntegral(List<JSONObject> afterClassIntegrals) {
		return StreamApiUtils.toMap(afterClassIntegrals,
				afterClassIntegral -> afterClassIntegral.getString(USER_ID),
				afterClassIntegral -> Integer.parseInt(afterClassIntegral.getString("integral")));
	}

	/**
	 * 获取用户跟计数
	 *
	 * @param examIntegrals 考试积分
	 * @return {@code Map<String, Integer> }
	 * @throws
	 * @see List<JSONObject>
	 * @see Map<String, Integer>
	 */
	private Map<String, Integer> getUserWithCount(List<JSONObject> examIntegrals) {
		return StreamApiUtils.toMap(examIntegrals,
				examIntegral -> examIntegral.getString(USER_ID),
				examIntegral -> Integer.parseInt(examIntegral.getString(COUNT)));
	}

	/**
	 * 获取全部子级组织
	 *
	 * @param orgInfos    组织信息
	 * @param childrenOrg 子级组织
	 * @throws
	 * @see List<OrgInfo>
	 * @see Map<String, String>
	 */
	private void getAllChildrenOrg(List<OrgInfo> orgInfos, Map<String, String> childrenOrg) {

		for (OrgInfo org : orgInfos) {
			List<OrgInfo> children = org.getChildren();
			if (children.size() == 0) {
				childrenOrg.put(org.getId(), org.getPath());
			} else {
				getAllChildrenOrg(children, childrenOrg);
			}
		}
	}

	/**
	 * 获取组织统计数据
	 *
	 * @param page         分页
	 * @param pattern      模式
	 * @param organization 组织
	 * @param startTimeStr 启动时间str
	 * @param endTimeStr   结束时间 STR
	 * @param isPage       是否分页
	 * @return {@code ListRsp<OrgStatsBo> }
	 * @throws
	 * @see Page<OrgStatsBo>
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see boolean
	 * @see ListRsp<OrgStatsBo>
	 */
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

		if (userIds.size() == 0) {
			return PageUtil.getListResult(null);
		}
		// 获取所有的末梢组织
		Map<String, String> childrenOrg = collectChildrenOrg(organization);
		Map<String, List<UserInfo>> org_users = userInfos.stream()
				.filter(item -> StringUtils.isNotBlank(item.getOrganizationId()))
				.collect(Collectors.groupingBy(UserInfo::getOrganizationId));

		List<OrgStatsBo> result = Lists.newArrayList();
		List<UserStatsBo> userStatsBos = learnStatsMapper.userStatsSelect(userIds,
				Note.NoteType.REPORT.getCode(), startTime, endTime);
		List<JSONObject> examUserCount = remoteAdService.competitionListWithMinScore(
				TimeUtil.format6(startTime), TimeUtil.format6(endTime), null, minScore);
		for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
			String org_id = org.getKey();
			List<UserStatsBo> collect = new ArrayList<>();
			int orgUserCount = 0;
			int examCount = 0;
			if (org_users.containsKey(org_id)) {
				List<UserInfo> org_infos = org_users.get(org_id);
				collect = userStatsBos.stream()
						.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
								.collect(Collectors.toList()).contains(tmp.getResourceId()))
						.collect(Collectors.toList());
				examCount = examUserCount.stream()
						.filter(tmp -> org_infos.stream().map(UserInfo::getUserId)
								.collect(Collectors.toList()).contains(tmp.get(USER_ID)))
						.mapToInt(s -> Integer.parseInt(s.getString(COUNT))).sum();
				orgUserCount = (int) userInfos.stream()
						.filter(s -> StringUtils.isNotBlank(s.getOrganizationId())
								&& s.getOrganizationId().equals(org_id))
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

	/**
	 * 生成 OBJ
	 *
	 * @param collect      收集
	 * @param org_name     组织名称
	 * @param orgUserCount 组织用户计数
	 * @param examCount    考试计数
	 * @return {@code OrgStatsBo }
	 * @throws
	 * @see List<UserStatsBo>
	 * @see String
	 * @see Integer
	 * @see Integer
	 * @see OrgStatsBo
	 */
	private OrgStatsBo generateObj(List<UserStatsBo> collect, String org_name, Integer orgUserCount,
			Integer examCount) {
		OrgStatsBo res = new OrgStatsBo();
		res.setOrganizationData(org_name);
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

	/**
	 * 获取默认启动时间
	 *
	 * @param startTimeStr 启动时间str
	 * @param endTime      结束时间
	 * @return {@code Date }
	 * @throws
	 * @see String
	 * @see Date
	 * @see Date
	 */
	private Date getDefaultStartTime(String startTimeStr, Date endTime) {

		if (null != startTimeStr && !startTimeStr.isEmpty()) {
			return TimeUtil.toDate2(startTimeStr);
		}
		return DateUtils.addDays(endTime, -CommonConstant.THIRTY);

	}
}
