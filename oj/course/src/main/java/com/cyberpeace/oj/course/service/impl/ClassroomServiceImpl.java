package com.cyberpeace.oj.course.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.integratation.service.bo.ScreenRecordByNode;
import com.cyberpeace.oj.course.service.ClassroomSceneRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.*;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.IdAndBool;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardFriend;
import com.cyberpeace.common.model.room.DashboardGroup;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.course.service.bo.classroom.ClassroomStatisticsBo;
import com.cyberpeace.oj.ojbase.constant.*;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.context.NoteUniqueContext;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.bo.NoteBo;
import com.cyberpeace.oj.ojbase.service.bo.UserNoteScoreBo;
import com.cyberpeace.oj.ojbase.service.bo.post.ReportEvaBo;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Autowired
	private NoteService noteService;

	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private LessonPaperEvaluateMapper lessonPaperEvaluateMapper;

	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	@Autowired
	private TrainPlanMapper trainPlanMapper;

	@Autowired
	private RemoteAdService remoteAdService;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private RbRoomMapper rbRoomMapper;
	
	@Autowired
	private LessonRecordMapper lessonRecordMapper;
	
	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private ClassroomSceneRecordService classroomSceneRecordService;

	@Override
	public Classroom create(Classroom classroom, AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());
//		classroomValidateHandler.validateClassroomLessonExist(classroom.getLessonId(),
//				classroom.getOrganizationId());
		classroomValidateHandler.validateClassroomStartAndEnd(classroom.getStartTime(),
				classroom.getEndTime());
		classroomValidateHandler.validateClassroomTimeOverlap(classroom.getOrganizationId(),
				classroom.getStartTime(), classroom.getEndTime());
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			classroomValidateHandler.validatePlanExistById(classroom.getTrainPlanId());
		}

		// 课堂基本数据获取
		if (StringUtils.isBlank(classroom.getTrainPlanId())) {
			List<String> orgIds = classroomValidateHandler
					.validateClassroomOrgHasUser(classroom.getOrganizationId());
			classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		}
		classroom.setResourceId(IDGeneratorUtil.getID());

		// 分组数据
		List<String> userIds = remoteUserService.idsByOrgId(classroom.getOrganizationId());

		// 如果组织下不包含创建者则把创建者加入
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoService.initClassroomGroupInfo(
				classroom.getResourceId(), courseLesson, userIds, CommonConstant.ONE);

		// 如果课程无访问权限 把组织数据加入
		boolean changCourse = changeCourse(classroom.getOrganizationId(), course);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				classroomMapper.insert(classroom);
				classroomGroupInfoService.saveBatch(groupInfoList);
				if (changCourse) {
					courseMapper.updateById(course);
				}
			}
		});

		return classroom;
	}

	@Override
	public Boolean updateClassroom(Classroom classroom, AuthContext authContext) {

		// 时间校验
		classroomValidateHandler.validateClassroomStartAndEnd(classroom.getStartTime(),
				classroom.getEndTime());
		classroomValidateHandler.validateUpdateClassroomTimeOverlap(classroom.getResourceId(),
				classroom.getOrganizationId(), classroom.getStartTime(), classroom.getEndTime());
		// 唯一索引 课时+组织id唯一性校验
//		classroomValidateHandler.validateClassroomLessonExistExcludeItself(classroom.getLessonId(), classroom.getOrganizationId(), classroom);

		// 课程权限校验
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());

		// 课程表校验
		Classroom existRoom = classroomValidateHandler
				.validateClassroomExist(classroom.getResourceId());

		// 训练计划校验
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			classroomValidateHandler.validatePlanExistById(classroom.getTrainPlanId());
		}

		validateChangeClassroom(classroom);

		// 组织数据获取
		if (StringUtils.isBlank(classroom.getTrainPlanId())) {
			List<String> orgIds = remoteOrganizationService
					.childrenList(classroom.getOrganizationId(), true);
			classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		}

		// 判断群组是否群组需要改变
		boolean changeGroup = changeGroup(existRoom, classroom, courseLesson);
		List<ClassroomGroupInfo> groupInfos = changeGroup ? reInitGroup(classroom, courseLesson)
				: Lists.newArrayList();

		// 课程修改
		boolean changeCourse = changeCourse(classroom.getOrganizationId(), course);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				classroomMapper.updateById(classroom);

				if (changeCourse) {
					courseMapper.updateById(course);
				}

				if (changeGroup) {
					classroomGroupInfoMapper.deleteByRoom(classroom.getResourceId());
					classroomGroupInfoService.saveBatch(groupInfos);
				}
			}
		});

		return CommonConstant.TRUE;

	}

	/** 判断是否需要修改课程*/
	private boolean changeCourse(String orgId, Course course) {

		// 校验是否为自定义
		if (course.getReadAuthType().intValue() == AuthTable.AuthType.ALL_SHARE_MODE.getCode()
				.intValue()) {
			return CommonConstant.FALSE;
		}

		// 校验组织是否改变
		List<String> orgIds = remoteOrganizationService.childrenList(orgId, true);
		List<String> readIds = JsonUtil.toJsonObjectArray(course.getReadAuthIds(), String.class);
		if (readIds.containsAll(orgIds)) {
			return CommonConstant.FALSE;
		}

		// 将不存在的组织加入到授权中
		readIds.addAll(orgIds);
		List<String> finalIds = StreamApiUtils.distinct(readIds);
		course.setReadAuthIds(JsonUtil.arrayToString(finalIds));

		return CommonConstant.TRUE;
	}

	/** 判断是否修改群组*/
	private boolean changeGroup(Classroom current, Classroom update, CourseLesson courseLesson) {
		boolean enableChange = !current.getLessonId().equals(update.getLessonId());
		enableChange |= !current.getOrganizationId().equals(update.getOrganizationId());
		enableChange &= courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
				.getCode().intValue();

		return enableChange;
	}

	/** 出新初始化分组数据*/
	private List<ClassroomGroupInfo> reInitGroup(Classroom classroom, CourseLesson courseLesson) {

		// 用户查询及校验
		List<String> orgUserIds = remoteUserService.idsByOrgId(classroom.getOrganizationId());
		return classroomGroupInfoService.initClassroomGroupInfo(classroom.getResourceId(),
				courseLesson, orgUserIds, CommonConstant.ONE);
	}

	/**
	 * 校验数据课堂表数据是否可以改变
	 * @param classroom
	 * @return
	 */
	private void validateChangeClassroom(Classroom classroom) {

		List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper
				.selectByRoom(classroom.getResourceId());
		List<String> groupIds = StreamApiUtils.map(groupInfos, ClassroomGroupInfo::getResourceId);

		// 无群组则无场景
		if (CollectionUtils.isEmpty(groupIds)) {
			return;
		}

		List<OjLocalScene> localScenes = ojLocalSceneMapper.selectByGroupIds(groupIds);
		if (CollectionUtils.isNotEmpty(localScenes)) {
			log.error("Classroom has scene is running! classroom_id=[{}]",
					classroom.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.CLASS_SCENE_IS_RUNNING);
		}

	}

	@Override
	public List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery,
			AuthContext authContext) {
		List<ClassroomBo> classroomBos = new ArrayList<>();

		// 权限过滤
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);
		if (!CollectionUtils.isEmpty(authCourseIds)) {

			// 公开过滤
			List<String> publicCourseIds = courseMapper.selectIdsByCategory(authCourseIds, null,
					authContext.getIsWeb());
			if (!CollectionUtils.isEmpty(publicCourseIds)) {

				// 课程表数据查询
				List<Classroom> classrooms = classroomMapper.selectListByCourse(publicCourseIds,
						roomListQuery);
				// filter continuous
				classroomBos = this.filterContinuous(classrooms);
			}
		}

		// 单兵 团队 红蓝对抗
		List<ClassroomBo> crClassroom = crClassroom(roomListQuery.getStartTime(),
				roomListQuery.getEndTime(), authContext.getUserId());
		classroomBos.addAll(crClassroom);

		// 竞赛赛事
		List<ClassroomBo> adClassroom = adClassroom(roomListQuery.getStartTime(),
				roomListQuery.getEndTime(),
				authContext.getSuperAdmin() ? CommonConstant.EMPTY_STRING
						: authContext.getUserId());
		classroomBos.addAll(adClassroom);

		// 根据开始时间转换为map
		List<ClassroomBo> splitTimeBos = StreamApiUtils.flatMap(classroomBos,
				classroomBo -> splitTimeByDay(classroomBo));
		Map<String, List<ClassroomBo>> startTimeMap = StreamApiUtils.groupBy(splitTimeBos,
				classroomBo -> TimeUtil.format6(classroomBo.getStartTime()));

		// 对key进行排序
		List<String> sortKeys = Lists.newArrayList(startTimeMap.keySet()).stream().sorted()
				.collect(Collectors.toList());

		return StreamApiUtils.map(sortKeys, key -> {
			TimeScheduleBo scheduleBo = new TimeScheduleBo();
			scheduleBo.setDate(key);
			scheduleBo.setClassrooms(startTimeMap.get(key));
			return scheduleBo;
		});
	}

	/** 数据拆分，数据不能跨天，跨几天则为几条*/
	private List<ClassroomBo> splitTimeByDay(ClassroomBo classroomBo) {

		List<ClassroomBo> classroomBos = Lists.newArrayList(classroomBo);

		// 不跨天则直接返回
		long intervalDay = TimeUtil.getIntervalDay(classroomBo.getStartTime(),
				classroomBo.getEndTime());
		if (intervalDay == CommonConstant.ZERO) {
			return classroomBos;
		}

		// 跨天
		for (long i = 1; i <= intervalDay; i++) {

			ClassroomBo preBo = classroomBos.get((int) (i - CommonConstant.ONE));
			Date addDate = TimeUtil.addDates(classroomBo.getStartTime(), (int) i);
			Date endOfDay = TimeUtil.getEndOfDay(addDate);
			Date startOfDay = TimeUtil.getStartOfDay(addDate);

			// 如果结束时间刚好等于下一天的零点 则不扩展 并且把上一个的结束时间改为课程表的结束时间
			if (classroomBo.getEndTime().equals(startOfDay)
					|| classroomBo.getEndTime().before(startOfDay)) {
				preBo.setEndTime(classroomBo.getEndTime());
				break;
			}

			ClassroomBo copy = BeanUtils.copy(classroomBo, ClassroomBo.class);
			copy.setStartTime(startOfDay);
			copy.setEndTime(endOfDay);
			classroomBos.add(copy);
		}

		// 如果多于一个，说明跨天 则将第一个课程表结束时间改为开始当日的最后一秒
		if (classroomBos.size() > CommonConstant.ONE.intValue()) {
			classroomBos.get(classroomBos.size() - CommonConstant.ONE)
					.setEndTime(classroomBo.getEndTime());
			classroomBo.setEndTime(TimeUtil.getEndOfDay(classroomBo.getStartTime()));
		}

		return classroomBos;

	}

	/**
	 * 竞赛比赛
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @return
	 */
	private List<ClassroomBo> adClassroom(Date startTime, Date endTime, String userId) {

		List<CalendarCompetitionBo> competitionBos = remoteAdService.competitionList(startTime,
				endTime, userId);
		return StreamApiUtils.map(competitionBos,
				calendar -> BeanUtils.copy(calendar, ClassroomBo.class));
	}

	/**
	 * 团队训练 红蓝对抗
	 * @param startTime
	 * @param endTime
	 * @param userId
	 * @return
	 */
	private List<ClassroomBo> crClassroom(Date startTime, Date endTime, String userId) {

		List<ClassroomBo> classroomBos = Lists.newArrayList();

		List<String> rbIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		if (CollectionUtils.isNotEmpty(rbIds)) {
			List<RbRoom> rbRooms = rbRoomMapper.selectBatchIds(rbIds);
			List<RbRoom> rooms = StreamApiUtils.filter(rbRooms,
					rbRoom -> startTime.before(rbRoom.getStartTime())
							&& endTime.after(rbRoom.getStartTime()));
			classroomBos.addAll(StreamApiUtils.map(rooms, rbRoom -> rbClassroom(rbRoom, userId)));
		}

		List<String> teamIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (CollectionUtils.isNotEmpty(teamIds)) {
			List<TeamRoom> teamRooms = teamRoomMapper.selectBatchIds(teamIds);
			List<TeamRoom> filter = StreamApiUtils.filter(teamRooms,
					teamRoom -> startTime.before(teamRoom.getStartTime())
							&& endTime.after(teamRoom.getStartTime()));
			classroomBos.addAll(
					StreamApiUtils.map(filter, teamRoom -> teamClassroom(teamRoom, userId)));
		}

		return classroomBos;
	}

	/** 团队训练房间数据转换*/
	private ClassroomBo teamClassroom(TeamRoom teamRoom, String userId) {
		ClassroomBo classroomBo = new ClassroomBo();
		classroomBo.setName(teamRoom.getName());
		classroomBo.setResourceId(teamRoom.getResourceId());
		classroomBo.setClassify(ClassroomType.TEAM.getCode());
		classroomBo.setCreateUserId(teamRoom.getCreateUserId());
		classroomBo.setStartTime(teamRoom.getStartTime());
		classroomBo.setEndTime(teamRoom.getEndTime());

		CrRole role = crRoleMapper.getCrRoleByRoomIdAndUserId(teamRoom.getResourceId(), userId);
		classroomBo.setRoleGroup(role.getRoleGroup());
		return classroomBo;
	}

	/** 红蓝房间转换*/
	private ClassroomBo rbClassroom(RbRoom rbRoom, String userId) {
		ClassroomBo classroomBo = new ClassroomBo();
		classroomBo.setClassify(ClassroomType.RB.getCode());
		classroomBo.setName(rbRoom.getName());
		classroomBo.setResourceId(rbRoom.getResourceId());

		classroomBo.setCreateUserId(rbRoom.getCreateUserId());
		classroomBo.setStartTime(rbRoom.getStartTime());
		classroomBo.setEndTime(rbRoom.getEndTime());

		CrRole role = crRoleMapper.getCrRoleByRoomIdAndUserId(rbRoom.getResourceId(), userId);
		classroomBo.setRoleGroup(role.getRoleGroup());

		return classroomBo;
	}

	@Override
	public List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext) {

		List<Classroom> classrooms = classroomMapper.selectList(roomListQuery);
		Set<String> existClassroom = classrooms.stream().map(Classroom::getResourceId).collect(Collectors.toSet());
		//管理员用户增加admin用户在自己创建的计划中的创建的计划
		if (authContext.getIsAdmin()) {
			roomListQuery.setUserId(null);
			List<Classroom> superAdminClassrooms = classroomMapper.selectList(roomListQuery);
			for (Classroom classroom : superAdminClassrooms) {
				if (classroom.getTrainPlanId() != null) {
					TrainPlan trainPlan = trainPlanMapper.selectById(classroom.getTrainPlanId());
					if (trainPlan != null
							&& trainPlan.getCreateUserId().equals(authContext.getUserId())
							&& existClassroom.add(classroom.getResourceId())) {
						classrooms.add(classroom);
					}
				}
			}
		}

		// 过滤已删除组织关联数据
		List<String> orgs = remoteOrganizationService.childrenList("", true);
		classrooms = classrooms.stream().filter(classroom -> orgs.contains(classroom.getOrganizationId())).collect(Collectors.toList());

		return StreamApiUtils.map(classrooms, this::entityToBo);
	}

	@Override
	public ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId) {

		// 基本数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);

		// 查询组织下用户
		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
				true);

		List<ClassroomSign> signList = classroomSignMapper.selectByRoom(classroomId);
		Map<String, ClassroomSign> signMap = StreamApiUtils.toMap(signList,
				ClassroomSign::getUserId, classroomSign -> classroomSign);

		List<ClassroomMonitorBo> monitorBos = StreamApiUtils.map(userInfos,
				userInfo -> userToMonitorBo(userInfo, signMap));

		// 组织信息外部获取 防止用户过多调用外部接口
		Map<String, String> orgNameMap = Maps.newHashMap();
		for (ClassroomMonitorBo monitorBo : monitorBos) {

			if (StringUtils.isBlank(orgNameMap.get(monitorBo.getOrganizationId()))) {
				// 组织信息查询
				String orgName = remoteOrganizationService
						.orgNameById(monitorBo.getOrganizationId());
				orgNameMap.put(monitorBo.getOrganizationId(), orgName);
			}
			monitorBo.setOrganizationName(orgNameMap.get(monitorBo.getOrganizationId()));
		}

		return PageUtil.getListResult(monitorBos, page, monitorBos.size());

	}

	@Override
	public PageReportBo selectClassroomReport(String classroomId, String userName, Page page) {

		// 基本数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), false);

		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
				true);

		List<UserInfo> filterUsers = StreamApiUtils.filter(userInfos,
				userInfo -> StringUtils.isBlank(userName)
						|| userInfo.getUserName().contains(userName)
						|| userInfo.getName().contains(userName));

		List<UserInfo> pageUser;
		if (CollectionUtils.isEmpty(page.getOrders())) {
			pageUser = StreamApiUtils.pageByList(filterUsers, page.getCurrent(), page.getSize());
		} else {
			pageUser = filterUsers;
		}

		Map<String, String> orgMap = remoteOrganizationService
				.childrenMap(classroom.getOrganizationId(), true);

		// 供评估人员使用
		Map<String, UserInfo> evaluateUser = Maps.newHashMap();
		List<ClassroomReportBo> data = StreamApiUtils.map(pageUser,
				userInfo -> userToReportBo(userInfo, courseLesson, orgMap, evaluateUser));
		return new PageReportBo(data, (long) filterUsers.size());
	}

	@Override
	public ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext) {

		// 基本数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());

		// 查询组织用户
		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
				true);
		List<String> orgUserIds = StreamApiUtils.map(userInfos, UserInfo::getUserId);
		List<ClassroomSign> signList = classroomSignMapper.selectByRoom(classroom.getResourceId());
		List<ClassroomSign> signIns = StreamApiUtils.filter(signList,
				classroomSign -> orgUserIds.contains(classroomSign.getUserId())
						&& classroomSign.getSignIn());
		List<String> signUsers = StreamApiUtils.map(signIns, ClassroomSign::getUserId);

		// 查询所有笔记
		List<Note> notes = new ArrayList<>();
		// 仅统计签到过的用户
		if (signUsers.size() != 0) {
			NoteListQuery listQuery = NoteListQuery.builder()
					.noteType(Note.NoteType.CLASS_REPORT.getCode())
					.businessId(courseLesson.getResourceId())
					.businessType(Note.NoteResourceType.LESSON.getCode()).userIds(signUsers)
					.build();
			notes = noteMapper.selectByListQuery(listQuery, authContext.getIsWeb());
		}

		// 评估数据查询
		List<String> noteIds = StreamApiUtils.map(notes, Note::getResourceId);
		List<NoteEvaluate> evaluates = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(noteIds)) {
			List<NoteEvaluate> evaluateList = noteEvaluateMapper.selectBatchNoteIds(noteIds,
					authContext.getIsWeb());
			evaluates.addAll(evaluateList);
		}

		// 签到信息统计
		SignInfoBo signInfoBo = statisticSignInfo(classroom, orgUserIds, evaluates, signIns);

		// 成绩数据统计
		GradeDistributionBo distributionBo = statisticGrade(evaluates);

		// 排名数据统计
		List<UserNoteScoreBo> topNum = statisticTopNum(evaluates, notes, 20);
		return ClassroomStatisticBo.builder().signInfo(signInfoBo)
				.top20(topNum).gradeDistribution(distributionBo).build();
	}

	/**
	 * 课堂习题数据处理
	 *
	 * @param paperEvaluate
	 * @param paper
	 * @return
	 */
	private Map<String, LessonPracticeListBo> handlerPractice(LessonPaperEvaluate paperEvaluate,
			Paper paper, Boolean viewAnswer, AuthContext authContext, String lessonId) {

		Map<String, PracticeSolvedBo> solvedBoMap = Maps.newHashMap();
		if (null != paperEvaluate) {
			// 获取解题记录
			List<PracticeSolvedBo> solvedBos = JsonUtil.toJsonObjectArray(paperEvaluate.getRecord(),
					PracticeSolvedBo.class);
			solvedBoMap.putAll(StreamApiUtils.toMap(solvedBos, PracticeSolvedBo::getResourceId,
					practiceSolvedBo -> practiceSolvedBo));
		}

		// 查询习题内容 转换数据类型
		List<OperationLessonBo> operationLessonBos = practiceOperationRefService
				.selectByPaperId(lessonId, paper.getResourceId(), viewAnswer, false, authContext);

		// 实操习题转换
		List<LessonOperationBo> lessonOperationBos = StreamApiUtils.map(operationLessonBos,
				operationBo -> {
					LessonOperationBo lessonOperationBo = BeanUtils.copy(operationBo,
							LessonOperationBo.class);
					lessonOperationBo
							.setResolvedDetail(solvedBoMap.get(operationBo.getResourceId()));
					return lessonOperationBo;
				});

		// 理论习题转换
		List<TheoryLessonBo> theoryLessonBos = practiceTheoryRefService
				.selectByPaper(paper.getResourceId(), viewAnswer);
		List<LessonTheoryBo> theoryBoList = StreamApiUtils.map(theoryLessonBos, theoryLessonBo -> {
			LessonTheoryBo theoryBo = BeanUtils.copy(theoryLessonBo, LessonTheoryBo.class);
			theoryBo.setResolvedDetail(solvedBoMap.get(theoryBo.getResourceId()));
			return theoryBo;
		});

		// 理论习题数据分组
		Map<Integer, List<LessonTheoryBo>> practiceMap = StreamApiUtils.groupBy(theoryBoList,
				TheoryLessonBo::getQuestionType);

		// 新建map存储数据
		Map<Integer, List<? extends TheoryLessonBo>> allMap = Maps.newHashMap();
		allMap.putAll(practiceMap);
		if (CollectionUtils.isNotEmpty(lessonOperationBos)) {
			allMap.put(PracticeTheory.TheoryType.OPERATION.getCode(), lessonOperationBos);
		}

		// 组装习题数据
		Map<String, LessonPracticeListBo> listBoMap = Maps.newHashMap();
		allMap.forEach((key, value) -> {
			Double totalScore = StreamApiUtils.mapToDouble(value, TheoryLessonBo::getScore);
			LessonPracticeListBo practiceListBo = new LessonPracticeListBo();
			practiceListBo.setPracticeBos(value);
			practiceListBo.setCount(value.size());
			practiceListBo.setTotalScore(totalScore);
			listBoMap.put(EnumUtils.getEnumDescByCode(key, PracticeTheory.TheoryType.class),
					practiceListBo);
		});

		return listBoMap;
	}

	@Override
	public ClassroomPracticeDetailBo practiceDetail(String classroomId, String lessonId,
			String userId) {
		CourseLesson courseLesson = courseValidateHandler.validateLessonExist(lessonId, false);

		// 课后练习评估数据查询
		LessonPaperEvaluate paperEvaluate = lessonPaperEvaluateMapper.selectByUserAndLesson(userId,
				lessonId);

		if (null == paperEvaluate) {
			return null;
		}

		Paper paper = paperMapper.selectById(courseLesson.getPaperId());

		// 提交用户处理
		UserInfo userInfo = remoteUserService.getById(userId);
		AuthContext authContext = new AuthContext();
		authContext.setUserId(userInfo.getUserId());
		authContext.setIsWeb(false);

		// 处理习题数据
		Map<String, LessonPracticeListBo> handlerPractice = handlerPractice(paperEvaluate, paper,
				true, authContext, lessonId);

		// 习题总数
		Integer practiceCount = 0;
		for (LessonPracticeListBo item : handlerPractice.values()) {
			practiceCount += item.getCount();
		}

		ClassroomPracticeDetailBo classroomPracticeDetailBo = ClassroomPracticeDetailBo.builder()
				.lessonName(courseLesson.getName()).build();
		classroomPracticeDetailBo.setAccuracy(paperEvaluate.getAccuracy());
		classroomPracticeDetailBo.setLevel(paperEvaluate.getLevel());
		classroomPracticeDetailBo.setSubmitUserName(userInfo.getName());
		classroomPracticeDetailBo.setPracticeList(handlerPractice);
		classroomPracticeDetailBo.setPracticeCount(practiceCount);
		return classroomPracticeDetailBo;
	}

	@Override
	public ClassroomPracticeBo practiceStatistic(ClassroomPracticeContext cpContext, Page page) {

		// 判断练习是否下发
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(cpContext.getLessonId(), false);

		Paper paper = paperMapper.selectById(courseLesson.getPaperId());
		String readAuthIds = paper.getReadAuthIds();
		if (null == readAuthIds) {
			readAuthIds = "[]";
		}

		boolean isReleased = true;
		boolean hasPractice = true;
		if (!readAuthIds.contains(cpContext.getOrganizationId())) {
			isReleased = false;
			if (paper.getPracticeCount().equals(0)) {
				hasPractice = false;
			}
		}

		ClassroomPracticeBo classroomPracticeBo = ClassroomPracticeBo.builder()
				.hasPractice(hasPractice).released(isReleased).build();

		if (hasPractice) {
			List<UserInfo> userInfoList = remoteUserService
					.ListByOrgId(cpContext.getOrganizationId(), true);
			List<String> userIds = Lists.newArrayList();

			// 姓名搜索
			String username = cpContext.getUsername();
			boolean searchName = StringUtils.isNotBlank(username);
			for (UserInfo userInfo : userInfoList) {
				if (searchName) {
					if (!userInfo.getName().contains(username)) {
						continue;
					}
				}
				userIds.add(userInfo.getUserId());
			}

			List<PracticePaperEvaluateBo> practiceInfos = Lists.newArrayList();

			if (userIds.isEmpty()) {
				ListRsp<PracticePaperEvaluateBo> practiceInfosRsp = PageUtil
						.getListResult(practiceInfos, page, 0);
				classroomPracticeBo.setData(practiceInfosRsp);
				return classroomPracticeBo;
			}

			List<LessonPaperEvaluate> lessonPaperEvaluates = lessonPaperEvaluateMapper
					.selectByLessonAndUsers(userIds, cpContext.getLessonId());

			Map<String, LessonPaperEvaluate> idToObj = StreamApiUtils.toMap(lessonPaperEvaluates,
					LessonPaperEvaluate::getSubmitUserId,
					lessonPaperEvaluate -> lessonPaperEvaluate);

			Map<String, String> userIdToName = Maps.newHashMap();
			Map<String, String> OrgIdToName = Maps.newHashMap();

			for (UserInfo userInfo : userInfoList) {
				if (!userIds.contains(userInfo.getUserId())) {
					continue;
				}

				PracticePaperEvaluateBo practicePaperEvaluateBo = new PracticePaperEvaluateBo();

				// 判断是否已提交
				Boolean isCommitted = cpContext.getIsCommit();
				// 判断是否已批改
				Boolean isMarked = cpContext.getIsMark();

				LessonPaperEvaluate lessonPaperEvaluate = idToObj.get(userInfo.getUserId());

				if (null != isCommitted) {
					if (isCommitted && null == lessonPaperEvaluate) {
						continue;
					}
					if (!isCommitted && null != lessonPaperEvaluate) {
						continue;
					}
				}

				if (null != lessonPaperEvaluate) {

					Integer level = lessonPaperEvaluate.getLevel();

					if (null != isMarked) {
						if (isMarked && null == level) {
							continue;
						}
						if (!isMarked && null != level) {
							continue;
						}
					}

					if (level != null) {
						practicePaperEvaluateBo.setCorrectTime(lessonPaperEvaluate.getUpdateTime());
					}

					practicePaperEvaluateBo.setSubmitState(true);
					practicePaperEvaluateBo.setAccuracy(lessonPaperEvaluate.getAccuracy());
					practicePaperEvaluateBo.setLevel(level);
					practicePaperEvaluateBo.setSubmitTime(lessonPaperEvaluate.getCreateTime());

					String corrector = lessonPaperEvaluate.getCorrector();
					if (corrector != null) {
						if (userIdToName.containsKey(corrector)) {
							corrector = userIdToName.get(corrector);
						} else {
							String corrector_name = remoteUserService.getById(corrector).getName();
							userIdToName.put(corrector, corrector_name);
							corrector = corrector_name;
						}
					}
					practicePaperEvaluateBo.setCorrector(corrector);

				} else if (null != isMarked && isMarked) {
					continue;
				}

				practicePaperEvaluateBo.setName(userInfo.getName());
				practicePaperEvaluateBo.setUserId(userInfo.getUserId());
				practicePaperEvaluateBo.setUsername(userInfo.getUserName());
				// 组织信息查询
				String organizationId = userInfo.getOrganizationId();
				if (null != organizationId) {
					String orgName = OrgIdToName.get(organizationId);
					if (null == orgName) {
						orgName = remoteOrganizationService
								.orgNameById(userInfo.getOrganizationId());
						OrgIdToName.put(organizationId, orgName);
					}
					practicePaperEvaluateBo.setOrganizationName(orgName);
				}

				practiceInfos.add(practicePaperEvaluateBo);

			}
			ListRsp<PracticePaperEvaluateBo> practiceInfosRsp = PageUtil
					.getListResult(practiceInfos, page, practiceInfos.size());
			classroomPracticeBo.setData(practiceInfosRsp);

		}

		return classroomPracticeBo;
	}

	@Override
	public Integer deleteClassroom(String classroomId, AuthContext authContext) {

		// 数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		validateChangeClassroom(classroom);

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				// 课堂数据删除
				classroomGroupInfoMapper.deleteByRoom(classroomId);
				return classroomMapper.deleteById(classroomId);
			}
		});
	}

	@Override
	public DashboardInfo getChatUsers(String roomId, UserInfo userContext) {

		Classroom classroom = classroomMapper.selectById(roomId);
		if (null == classroom) {
			log.error("classroom not found! classroom_id=[{}]", roomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		List<String> userIds = remoteUserService.idsByOrgId(classroom.getOrganizationId());
		if (CollectionUtils.isEmpty(userIds)) {
			log.error("Classroom user is null! classroom_id=[{}]", roomId);
			return null;
		}

		// 将超级管理员和创建者内置进去
		if (!userIds.contains(ResourceImportConstant.SUPER_ADMIN_HASH)) {
			userIds.add(ResourceImportConstant.SUPER_ADMIN_HASH);
		}
		if (!userIds.contains(classroom.getCreateUserId())) {
			userIds.add(classroom.getCreateUserId());
		}

		// 获取用户组装
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		DashboardInfo dashboardInfo = new DashboardInfo();
		List<DashboardUser> friendUsers = Lists.newArrayList();

		for (String userId : userIds) {
			UserInfo userInfo = userInfoMap.get(userId);
			if (userInfo == null) {
				log.error("Some users are missing! user_id=[{}]", userId);
				continue;
			}
			DashboardUser dashboardUser = userToDashUser(userInfo, ClassroomRole.STUDENT.getDesc());

			// 如果用户为课程表创建者则为教员
			if (userId.equals(classroom.getCreateUserId())) {
				dashboardUser.setRole(ClassroomRole.TEACHER.getDesc());
			}

			// 如果是当前用户不加进朋友列表 进入下一次循环
			if (userContext.getUserId().equals(userId)) {
				dashboardInfo.setMine(dashboardUser);
				continue;
			}

			friendUsers.add(dashboardUser);
		}

		// 如果超级管理员或者创建者不在该房间中
		if (null == dashboardInfo.getMine() && (userContext.isSuperuser()
				|| userContext.getUserId().equals(classroom.getCreateUserId()))) {
			String role = userContext.getUserId().equals(classroom.getCreateUserId())
					? ClassroomRole.TEACHER.getDesc()
					: ClassroomRole.STUDENT.getDesc();
			DashboardUser mine = userToDashUser(userContext, role);
			dashboardInfo.setMine(mine);
		}

		// 好友列表组装
		DashboardFriend dashboardFriend = new DashboardFriend();
		dashboardFriend.setGroupname(DashboardFriend.GROUP_NAME.FRIEND_LIST.getDesc());
		dashboardFriend.setId(CommonConstant.ONE);
		dashboardFriend.setList(friendUsers);
		dashboardInfo.setFriend(Lists.newArrayList(dashboardFriend));

		// 群组组装
		DashboardGroup group = new DashboardGroup(ClassroomRole.CLASSROOM.getDesc());
		group.setId(classroom.getResourceId());
		group.setMembers(userIds);
		dashboardInfo.setGroup(Lists.newArrayList(group));

		return dashboardInfo;
	}

	@Override
	public List<DashboardUser> getChatGroupUsers(String roomId, String groupId) {

		// 课堂数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(roomId);

		// 用户信息获取
		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
				true);
		if (CollectionUtils.isEmpty(userInfos)) {
			return Lists.newArrayList();
		}

		// 超级管理员和创建者加入
		Map<String, String> userMap = StreamApiUtils.toMap(userInfos, UserInfo::getUserId,
				UserInfo::getUserId);
		if (null == userMap.get(ResourceImportConstant.SUPER_ADMIN_HASH)) {
			UserInfo superAdmin = remoteUserService
					.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
			userMap.put(superAdmin.getUserId(), superAdmin.getUserId());
			userInfos.add(superAdmin);
		}
		if (null == userMap.get(classroom.getCreateUserId())) {
			UserInfo create = remoteUserService.getById(classroom.getCreateUserId());
			userInfos.add(create);
		}

		// 数据转换
		return StreamApiUtils.map(userInfos, userInfo -> {
			DashboardUser dashboardUser = userToDashUser(userInfo, ClassroomRole.STUDENT.getDesc());
			if (userInfo.getUserId().equals(classroom.getCreateUserId())) {
				dashboardUser.setRole(ClassroomRole.TEACHER.getDesc());
			}
			return dashboardUser;
		});
	}

	@Override
	public void removeClassrooms(List<String> orgIds) {
		if (!orgIds.isEmpty()) {
			classroomMapper.deleteByOrgIds(orgIds);
		}
	}

	@Override
	public IPage<ClassroomBo> selectPage(ClassRoomPageQuery roomListQuery,
										 AuthContext authContext) {
		Page page = roomListQuery.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(authContext);
		IPage<Classroom> result = classroomMapper.page(page, roomListQuery.getUserId(),
				roomListQuery.getTrainPlanId(), roomListQuery.getChildrenOrg(), authContext.getIsAdmin(), courseIds);

		return result.convert(classroom -> entityToBo(classroom));

	}

	@Override
	public List<String> selectIdsByPlanIds(List<String> ids) {
		return classroomMapper.selectIdsByPlanIds(ids);
	}

	@Override
	public List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover) {

		// excel数据读取
		ExcelDataHandler dataHandler = new ExcelDataHandler(realPath, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);

		// 基本数据校验
		validateDataLength(sheetData);

		// 数据解析
		List<Classroom> classrooms = Lists.newArrayList();
		Map<String, TrainPlan> trainPlanMap = Maps.newHashMap();
		Map<String, String> orgMap = Maps.newHashMap();
		List<Course> changeCourses = Lists.newArrayList();

		//sheetData的第0个位置对应源文件的第二行，因此监测行数全部都要相差2
		for (int i = CommonConstant.TWO; i < sheetData.size()+CommonConstant.TWO; i++) {

			// 空数据不管
			if (!validateEmptyData(sheetData.get(i - CommonConstant.TWO), i)) {
				continue;
			}

			// 解析行
			TrainPlan trainPlan = parseTrainPlan(sheetData.get(i - CommonConstant.TWO),
					userInfo.getUserId(), cover, i, trainPlanMap);

			// 训练计划是否可覆盖 任务是否存在开始的
			validatePlanStartAndExist(trainPlan.getResourceId(), trainPlan.getName(), cover, i);

			Classroom classroom = parseClassroom(sheetData.get(i - CommonConstant.TWO), userInfo, i,
					orgMap);
			validateOrgUnderClassroomTime(trainPlan.getResourceId(), classroom.getOrganizationId(), classroom.getStartTime(),
					classroom.getEndTime(), i);

			Course course = courseMapper.selectById(classroom.getCourseId());
			if (null == course) {
				continue;
			}
			if (changeCourse(classroom.getOrganizationId(), course)) {
				changeCourses.add(course);
			}

			classroom.setTrainPlanId(trainPlan.getResourceId());
			classrooms.add(classroom);
		}

		// 数据插入前校验
		validateTime(classrooms);
		validateClassroomTime(classrooms);

		// 数据库插入前 最好再次校验训练计划 防止解析时间过长
		List<Classroom> coverRoomList = StreamApiUtils.flatMap(trainPlanMap.values(),
				trainPlan -> getCoverRoomByPlan(trainPlan.getName(), trainPlan.getCreateUserId(),
						cover));

		List<TrainPlan> insertTrainPlan = StreamApiUtils.filter(trainPlanMap.values(),
				trainPlan -> null == trainPlanMapper.selectById(trainPlan.getResourceId()));

		// 数据库插入
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				coverRoomList.forEach(classroom -> deleteClassroom(classroom.getResourceId(),
						AuthContextUtils.constructAuth(userInfo, false)));
				insertTrainPlan.forEach(trainPlan -> trainPlanMapper.insert(trainPlan));
				saveBatch(classrooms);
				changeCourses.forEach(course -> courseMapper.updateById(course));
			}
		});

		return classrooms;
	}

	@Override
	public String exportReport(String classroomId){
		String basePath = AttachmentPath.LESSON_HTML_TEMPLATE.getDesc();
		// 基本数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), false);
		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		//课程名称
		String initLessonName = lesson.getName();
		String lessonName = initLessonName.replace(" ","");
		String htmlPath = basePath + File.separator + lessonName;
		//清理目录
		File file = new File(htmlPath);
		if (file.exists()) {
			FileUtil.deleteDir(file);
		}
		FileUtil.mkdirs(htmlPath);

		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
				true);
		Map<String, String> orgMap = remoteOrganizationService
				.childrenMap(classroom.getOrganizationId(), true);
		Map<String, UserInfo> evaluateUser = Maps.newHashMap();
		List<ClassroomReportBo> data = StreamApiUtils.map(userInfos,
				userInfo -> userToReportBo(userInfo, courseLesson, orgMap, evaluateUser));
		//过滤实验报告为空的情况
		List<ClassroomReportBo> filterData = StreamApiUtils.filter(data, classroomReportBo -> classroomReportBo.getContent()!=null);
		if (filterData.size() == 0){
			log.error("[{}] report is empty", classroomId);
			throw new BusinessException(OjBusinessErrorCode.NO_REPORT_CAN_BE_EXPORTED);
		}
		for(ClassroomReportBo classroomReportBo: filterData){
			String initPersonName =
					classroomReportBo.getName() + "(" + classroomReportBo.getUserName() + ")-" + initLessonName + ClassroomExportReportConstant.SESSION_REPORT.getMsg();
			String personName = initPersonName.replace(" ","");
			String baseClassroomPath = htmlPath + File.separator + personName;
			JSONObject jsonObject = new JSONObject();
			String mdContent = classroomReportBo.getContent();
			//处理图片
			List<String> strList = LocalStringUtil.getMatchStr(mdContent, PatternConstant.MARKDOWN_IMAGE);
			for (String str : strList) {
				String imgUrl = str.substring(str.lastIndexOf("(") + 1, str.lastIndexOf(")"));
				try {
					//判断图片真实类型
					String realType = ImgUtil.imgType(new File(imgUrl));
					if (realType.equals("webp")) {
						//图片真实类型为webp时，html转pdf图片无法显示，需要将图片转成jpg
						String imgName = EnumUtils.getEnumDescByCode(AttachmentPath.UPLOAD_IMAGE.getCode(), AttachmentPath.class) + IDGeneratorUtil.getID() + ".jpg";
						ImgUtil.webpToJpg(imgUrl, imgName);
						imgUrl = imgName;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				imgUrl = "http://" + SystemPropUtil.getProp("BC_HOST") + imgUrl;
				log.info("imgUrl:" + imgUrl);
				mdContent = mdContent.replace(str, String.format(MdConstant.ONLY_URL_IMG, imgUrl));
			}
			//附件
			List<String> urlList = LocalStringUtil.getMatchStr(mdContent, PatternConstant.MARKDOWN_URL);
			int j = 0;
			for (String str: urlList) {
				j++;
				String fileUrl = str.substring(str.lastIndexOf("(") + 1, str.lastIndexOf(")"));
				if (!new File(fileUrl).isFile()) {
					continue;
				}
				FileUtil.copyFile(fileUrl,
						baseClassroomPath + File.separator + j + "." + FileUtil.getFileSuffix(fileUrl), false);
				fileUrl = j + "." + FileUtil.getFileSuffix(fileUrl);
				mdContent = mdContent.replace(str, fileUrl);
			}
			String reportContent = MdUtil.mdToHtmlForApiDoc(mdContent);
			//实验报告
			jsonObject.put("report_content", reportContent);
			//是否通过
			if (classroomReportBo.getPassed() == null) {
				jsonObject.put("passed", ClassroomExportReportConstant.NOT_APPROVED.getMsg());
			} else if (classroomReportBo.getPassed()) {
				jsonObject.put("passed", ClassroomExportReportConstant.YES.getMsg());
			} else {
				jsonObject.put("passed", ClassroomExportReportConstant.NO.getMsg());
			}
			//评分
			if (classroomReportBo.getScore() == null) {
				jsonObject.put("score", ClassroomExportReportConstant.NOT_APPROVED.getMsg());
			} else {
				jsonObject.put("score", classroomReportBo.getScore().toString());
			}
			//评语
			if (classroomReportBo.getEvaluateComment() == null) {
				jsonObject.put("evaluate_comment", ClassroomExportReportConstant.NOT_APPROVED.getMsg());
			} else {
				jsonObject.put("evaluate_comment", classroomReportBo.getEvaluateComment());
			}
			jsonObject.put("report_content_title", ClassroomExportReportConstant.LABORATORY_REPORT_TITLE.getMsg());
			jsonObject.put("score_title", ClassroomExportReportConstant.SCORE_TITLE.getMsg());
			jsonObject.put("passed_title", ClassroomExportReportConstant.PASSED_TITLE.getMsg());
			jsonObject.put("evaluate_comment_title", ClassroomExportReportConstant.EVALUATE_COMMENT_TITLE.getMsg());
			String destHtmlPath = baseClassroomPath + File.separator + personName + ".html";
			HtmlToPdfUtil.getHtmlTemplateInstance(jsonObject, HtmlTemplateEnum.LESSON, destHtmlPath);
			// 在同目录下生成pdf
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
			File pdfFile = new File(destPdfPath);
			pdfFile.renameTo(new File(baseClassroomPath + File.separator + initPersonName + ".pdf"));
			//删除html文件
			FileUtil.deleteDir(new File(destHtmlPath));
			// 录屏文件
			classroomSceneRecordService.downloadRecords(classroomId, classroomReportBo.getUserId(), baseClassroomPath);
			File baseClassroomFile = new File(baseClassroomPath);
			baseClassroomFile.renameTo(new File(htmlPath + File.separator + initPersonName));

		}
		File zipFile = new File(ZipUtil.toZip(htmlPath));
		String zipPath = basePath + File.separator + initLessonName + ClassroomExportReportConstant.LABORATORY_REPORT_SUFFIX.getMsg();
		zipFile.renameTo(new File(zipPath));
		FileUtil.deleteDir(new File(htmlPath));
		return zipPath;
	}

	@Override
	public List<String> selectTempTaskIdsByUser(UserInfo userContext) {

		return classroomMapper.selectTempTaskIdsByUser(userContext.getUserId(),userContext.isSuperuser()) ;
	}

    @Override
    public Boolean getRecordingButton(String roomId) {
		Classroom classroom = this.getById(roomId);
		if (null == classroom){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return classroom.getEnableRecording();
    }

	@Override
	public void updateRecordingButton(IdAndBool req) {
		Classroom classroom = this.getById(req.getResourceId());
		if (null == classroom){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		classroom.setEnableRecording(req.getBool());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				updateById(classroom);

				List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper
						.selectByRoom(classroom.getResourceId());
				List<String> groupIds = StreamApiUtils.map(groupInfos,
						ClassroomGroupInfo::getResourceId);
				if (groupIds.isEmpty()) {
					return;
				}
				List<OjLocalScene> ojLocalSceneList = ojLocalSceneMapper.selectByGroupIds(groupIds);
				if (ojLocalSceneList.isEmpty()) {
					return;
				}
				List<String> localSceneIds = StreamApiUtils.map(ojLocalSceneList,
						OjLocalScene::getLocalSceneId);
				localSceneService.switchRecording(localSceneIds, req.getBool());
			}
		});
	}

	@Override
	public ClassroomStatisticsBo getStatistics(String roomId) {
		Classroom classroom = this.getById(roomId);
		if (null == classroom) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		ClassroomStatisticsBo res = new ClassroomStatisticsBo();
		res.setResourceId(roomId);

		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			TrainPlan plan = trainPlanMapper.selectById(classroom.getTrainPlanId());
			res.setPlanName(null == plan ? "" : plan.getName());
		}
		Course course = courseMapper.selectById(classroom.getCourseId());
		res.setCourseName(null == course ? "" : course.getName());

		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		res.setLessonName(null == lesson ? "" : lesson.getName());

		UserInfo teacher = remoteUserService.getById(classroom.getCreateUserId());
		res.setTeacherName(teacher.getName());

		String orgName = remoteOrganizationService.orgNameById(classroom.getOrganizationId());
		res.setOrgName(orgName);

		UserPageContext pageContext = UserPageContext.builder().org(classroom.getOrganizationId())
				.username("").build();
		List<UserInfo> students = remoteUserService.getFuzzyQueryUserList(pageContext);
		List<String> studentIds = StreamApiUtils.map(students, UserInfo::getUserId);
		List<ReportEvaBo> reportList = noteMapper.selectClassReport(classroom.getLessonId(),
				studentIds);
		res.setFinishedCount(reportList.size());

		List<Integer> scoreList = reportList.stream().map(ReportEvaBo::getScore)
				.filter(Objects::nonNull).sorted().collect(Collectors.toList());
		if (!scoreList.isEmpty()) {
			res.setLowest(scoreList.get(0));
			res.setHighest(scoreList.get(scoreList.size() - 1));
		}
		for (ReportEvaBo evaBo : reportList) {
			if (evaBo.getUserId().equals(UserInfoContext.getUserContext().getUserId())) {
				res.setScore(evaBo.getScore());
			}
		}
		return res;
	}

	/**
	 * 判断起止时间是否相同或起始时间大于结束时间
	 *
	 * @param classrooms 起止时间
	 */
	private void validateTime(List<Classroom> classrooms) {
		for(Classroom cls:classrooms){
			if(cls.getStartTime().equals(cls.getEndTime()) || cls.getStartTime().after(cls.getEndTime())){
				log.error("[{}] endTime is over starTime", cls.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.END_GT_START_TIME);
			}
		}

	}

	private void validateClassroomTime(List<Classroom> classrooms) {
		if (CollectionUtils.isEmpty(classrooms)) {
			return;
		}

		Map<String, List<Classroom>> orgMap = StreamApiUtils.groupBy(classrooms,
				Classroom::getOrganizationId);
		for (String key : orgMap.keySet()) {
			timeIntersection(orgMap.get(key));
		}
	}

	/**
	 * 校验时间重合
	 * @param classrooms
	 */
	private void timeIntersection(List<Classroom> classrooms) {
		List<Classroom> sort = StreamApiUtils.sortByColumn(classrooms, Classroom::getEndTime);
		if (sort.size() < CommonConstant.TWO) {
			return;
		}

		for (int i = 1; i < sort.size(); i++) {
			Classroom previous = sort.get(i - 1);
			Classroom current = sort.get(i);
			if (previous.getEndTime().after(current.getStartTime())) {
				log.error("The endTime [{}] is after starTime [{}]", previous.getEndTime(), current.getStartTime());
				throw new BusinessException(OjBusinessErrorCode.TIME_INTERSECTION_UNDER_ORG);
			}
		}
	}

	private void validateOrgLessonSame(List<Classroom> classrooms) {
		if (CollectionUtils.isEmpty(classrooms)) {
			return;
		}

		// 根据课时和组织去重
		List<Classroom> distinct = StreamApiUtils.distinctByColumn(classrooms,
				classroom -> classroom.getOrganizationId() + classroom.getLessonId());
		if (distinct.size() < classrooms.size()) {
			List<String> ids = StreamApiUtils.getColumn(classrooms, Classroom::getResourceId);
			log.error("classrooms ids:[{}] is duplicate", ids.toString());
			throw new BusinessException(OjBusinessErrorCode.DUPLICATE_DATA_ORG_LESSON);
		}
	}

	/** 校验数据长度*/
	private void validateDataLength(List<List<String>> sheetData) {

		// 数据长度校验 excel数据读取略去了表头，所以需加2才是正确行数
		for (int i = 0; i < sheetData.size(); i++) {

			// 空数据不管
			if (!validateEmptyData(sheetData.get(i), i + CommonConstant.TWO)) {
				continue;
			}

			if (sheetData.get(i).size() <= CommonConstant.FIVE) {
				log.error("sheet data line [{}] is missing", i);
				throw new BusinessException(OjBusinessErrorCode.FILE_CONTENT_MISSING,
						i + CommonConstant.TWO);
			}

			if (sheetData.get(i).size() == CommonConstant.SIX
					&& sheetData.get(i).contains(CommonConstant.EMPTY_STRING)) {
				log.error("sheet data line [{}] is empty", i);
				throw new BusinessException(OjBusinessErrorCode.FILE_CONTENT_MISSING,
						i + CommonConstant.TWO);
			}
		}
	}

	/** 校验训练计划下课堂表是否开始 开始则提醒*/
	private void validatePlanStartAndExist(String planId, String planName, boolean cover,
			int rowIndex) {
		List<Classroom> classroomList = classroomMapper.selectByPlan(planId);

		if (!cover && CollectionUtils.isNotEmpty(classroomList)) {
			log.error("[{}] have bind classroom", planId);
			throw new BusinessException(OjBusinessErrorCode.LINE_TRAIN_PLAN_EXIST, rowIndex);
		}

		classroomList.forEach(classroom -> {
			if (classroom.getStartTime().before(new Date())) {
				log.error("classroom start time:[{}] before now",classroom.getStartTime());
				throw new BusinessException(OjBusinessErrorCode.CLASSROOM_START_NOT_COVER,
						planName);
			}
		});
	}

	@Override
	public void exportTrainPlan(UserInfo userInfo, String filePath) {
		// 数据查询及转换
		String userId =userInfo.isSuperuser() ? CommonConstant.EMPTY_STRING : userInfo.getUserId();

		List<TrainPlanRsp> trainPlanRspList = trainPlanMapper.selectEndedByUser(userId);

		List<TrainPlanExportBo> exportBoList = StreamApiUtils.flatMap(trainPlanRspList,
				trainPlan -> planToExportBo(userId, trainPlan));

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAIN_PLAN);
	}

	/** 导出的数据转换
	 *  会按照页面逻辑查询计划下的课程任务
	 * */
	private List<TrainPlanExportBo> planToExportBo(String userId, TrainPlanRsp trainPlan) {
		List<Classroom> classroomList = classroomMapper.selectByUserPlan(userId,trainPlan.getResourceId());
		List<String> createUserIds = StreamApiUtils.map(classroomList, Classroom::getCreateUserId);
		Set<String> orgIds = classroomList.stream().map(Classroom::getOrganizationId)
				.collect(Collectors.toSet());
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(createUserIds);
		Map<String, String> orgPathMap = Maps.newHashMap();
		Map<String, Integer> orgUserCountMap = Maps.newHashMap();
		for (String orgId : orgIds) {
			String orgPath = remoteOrganizationService.getOrgPath(orgId);
			orgPathMap.put(orgId, orgPath);
			// 人数
			UserPageContext pageContext = UserPageContext.builder().org(orgId).username("").build();
			List<UserInfo> userInfos = remoteUserService.getFuzzyQueryUserList(pageContext);
			orgUserCountMap.put(orgId, userInfos.size());
		}
		Map<String, Integer> lessonCountMap = Maps.newHashMap();
		for (Classroom classroom : classroomList) {
			if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
				lessonCountMap.putIfAbsent(classroom.getTrainPlanId(), 0);
				lessonCountMap.computeIfPresent(classroom.getTrainPlanId(), (k, v) -> v + 1);
			}
		}
		return StreamApiUtils.map(classroomList,
				classroom -> classRoomToExportBo(trainPlan.getName(), classroom, userNameMap,
						orgPathMap, orgUserCountMap, lessonCountMap));
	}

	/** 数据转换*/
	private TrainPlanExportBo classRoomToExportBo(String planName, Classroom classroom,
			Map<String, String> userNameMap, Map<String, String> orgPathMap,
			Map<String, Integer> orgUserCountMap, Map<String, Integer> lessonCountMap) {
		TrainPlanExportBo planExportBo = new TrainPlanExportBo();
		planExportBo.setTrainPLanName(planName);

		Course course = courseMapper.selectById(classroom.getCourseId());
		if (course == null) {
			log.error("Classroom lesson not found! classroom_id=[{}], lesson_id=[{}]",
					classroom.getResourceId(), classroom.getLessonId());
			throw new BusinessException(OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL);
		}

		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		if (null == lesson) {
			log.error("classroom binds lesson:[{}] doesn't exist", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		planExportBo.setCourseName(course.getName());
		planExportBo.setLessonName(lesson.getName());

		planExportBo.setStartTime(classroom.getStartTime());
		planExportBo.setEndTime(classroom.getEndTime());

		String orgPath = orgPathMap.get(classroom.getOrganizationId());
		if (StringUtils.isBlank(orgPath)) {
			log.error("Classroom organization is not found! classroom_id=[{}], org_id=[{}]",
					classroom.getResourceId(), classroom.getOrganizationId());
			throw new BusinessException(OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL);
		}
		planExportBo.setOrgPath(orgPath);

		planExportBo.setUserName(userNameMap.get(classroom.getCreateUserId()));
		planExportBo.setLessonCount(lessonCountMap.get(classroom.getTrainPlanId()));
		planExportBo.setStudentCount(orgUserCountMap.get(classroom.getOrganizationId()));

		return planExportBo;
	}

	/**
	 * 获取需要覆盖的
	 * @param planName
	 * @param userId
	 * @param cover
	 * @return
	 */
	private List<Classroom> getCoverRoomByPlan(String planName, String userId, boolean cover) {
		TrainPlan trainPlan = trainPlanMapper.selectByName(planName, userId);
		List classroomList = Lists.newArrayList();

		if (null == trainPlan) {
			return classroomList;
		}

		classroomList = classroomMapper.selectByPlan(trainPlan.getResourceId());
		if (CollectionUtils.isNotEmpty(classroomList) && !cover) {
			log.error("tran plan:[{}] exist", planName);
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_EXIST);
		}

		return classroomList;
	}

	/**
	 * 校验空数据
	 * @param rowData
	 * @return
	 */
	private boolean validateEmptyData(List<String> rowData, int rowIndex) {
		if (CollectionUtils.isEmpty(rowData)
				|| StringUtils.isBlank(String.join(CommonConstant.EMPTY_STRING, rowData))) {
			log.error("Row data is null! rowIndex=[{}]", rowIndex);
			return false;
		}

		return true;
	}

	/**
	 * 课堂数据解析
	 * @param roomData
	 * @param userInfo
	 * @return
	 */
	private Classroom parseClassroom(List<String> roomData, UserInfo userInfo, int rowIndex,
			Map<String, String> orgMap) {
		String courseName = roomData.get(CommonConstant.ONE);
		if (StringUtils.isBlank(courseName)) {
			log.error("courseName:[{}] is blank", courseName);
			throw new BusinessException(OjBusinessErrorCode.PLAN_COURSE_IS_NULL, rowIndex);
		}

		String courseId = courseMapper.selectByName(courseName);

		if (StringUtils.isBlank(courseId)) {
			log.error("courseId:[{}] is blank", courseId);
			throw new BusinessException(OjBusinessErrorCode.PLAN_COURSE_NOT_EXIST, rowIndex);
		}

		//查询当前用户有权限的所有课程
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);


		if(!authCourseIds.contains(courseId)){
			log.error("authCourseIds doesn't :[{}] is blank", courseId);
			throw new BusinessException(OjBusinessErrorCode.PLAN_COURSE_NO_PERMISSION, rowIndex);
		}

		String lessonName = roomData.get(CommonConstant.TWO);
		if (StringUtils.isBlank(lessonName)) {
			log.error("lessonName :[{}] is blank", lessonName);
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_IS_NULL, rowIndex);
		}

		String lessonId = courseLessonMapper.selectByCourseAndName(courseId, lessonName);
		if (StringUtils.isBlank(lessonId)) {
			log.error("lessonId :[{}] is blank", lessonId);
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_NOT_EXIST, rowIndex);
		}

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (!courseLesson.getIsPublic()) {
			log.error("courseLesson :[{}] isn't public", courseLesson.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_NOT_PUBLIC, rowIndex);
		}

		String startTime = roomData.get(CommonConstant.THREE);
		String endTime = roomData.get(CommonConstant.FOUR);
		if (StringUtils.isBlank(startTime)) {
			log.error("startTime :[{}] is blank", startTime);
			throw new BusinessException(OjBusinessErrorCode.PLAN_TIME_IS_NULL, rowIndex);
		}
		if (StringUtils.isBlank(endTime)) {
			log.error("endTime :[{}] is blank", endTime);
			throw new BusinessException(OjBusinessErrorCode.PLAN_TIME_IS_NULL, rowIndex);
		}

		// 时间格式校验
		if (null == TimeUtil.toDate1(endTime) || null == TimeUtil.toDate1(startTime)) {
			log.error("startTime :[{}] endTime :[{}] isn't format", startTime ,endTime);
			throw new BusinessException(OjBusinessErrorCode.TIME_FORMAT_ERROR, rowIndex);
		}

		if (TimeUtil.toDate1(endTime).before(TimeUtil.toDate1(startTime))) {
			log.error("startTime :[{}] endTime :[{}] isn't reasonable", startTime ,endTime);
			throw new BusinessException(OjBusinessErrorCode.PLAN_END_GT_START_TIME, rowIndex);
		}

		String orgTree = roomData.get(CommonConstant.FIVE);
		if (StringUtils.isBlank(orgTree)) {
			log.error("sheet data line [{}] org name :[{}] is blank", rowIndex, orgTree);
			throw new BusinessException(OjBusinessErrorCode.PLAN_ORG_IS_NULL, rowIndex);
		}
		// 组织数据获取
		if (StringUtils.isBlank(orgMap.get(orgTree))) {
			String orgId = remoteOrganizationService.getOrgIdByTree(orgTree);
			if (StringUtils.isBlank(orgId)) {
				log.error("orgId :[{}] is blank", orgId);
				throw new BusinessException(OjBusinessErrorCode.PLAN_ORG_NOT_EXIST, rowIndex);
			} else {
				orgMap.put(orgTree, orgId);
			}
		}

		// 基本数据填充
		Classroom classroom = new Classroom();
		classroom.setCreateUserId(userInfo.getUserId());
		classroom.setResourceId(IDGeneratorUtil.getID());
		classroom.setCourseId(courseId);
		classroom.setLessonId(lessonId);
		classroom.setStartTime(TimeUtil.toDate1(startTime));
		classroom.setEndTime(TimeUtil.toDate1(endTime));

		classroom.setOrganizationId(orgMap.get(orgTree));
		List<String> childrenList = remoteOrganizationService.childrenList(orgMap.get(orgTree),
				true);
		classroom.setOrganizationIds(JsonUtil.arrayToString(childrenList));

		return classroom;
	}

	/**
	 * 校验组织下课程表的开始时间和结束时间是否存在冲突
	 * @param planId
	 * @param orgId
	 * @param startTime
	 * @param endTime
	 * @param rowIndex
	 */
	private void validateOrgUnderClassroomTime(String planId, String orgId, Date startTime, Date endTime, int rowIndex) {
		List<Classroom> classrooms = classroomMapper.selectByOrgId(orgId);
		for (Classroom classroom : classrooms) {
			if (planId.equals(classroom.getTrainPlanId())) {
				continue;
			}

			boolean startIn = startTime.getTime() > classroom.getStartTime().getTime()
					&& startTime.getTime() < classroom.getEndTime().getTime();
			boolean endIn = endTime.getTime() > classroom.getStartTime().getTime()
					&& endTime.getTime() < classroom.getEndTime().getTime();
			boolean startAndEndEq = startTime.equals(classroom.getStartTime())
					&& endTime.equals(classroom.getEndTime());

			if (startIn || endIn || startAndEndEq) {
				log.error("start time:[{}] end time:[{}] isn't reasonable", startTime.getTime(), endTime.getTime());
				throw new BusinessException(OjBusinessErrorCode.IMPORT_CLASS_IN_SCHEDULES, rowIndex);
			}
		}
	}

	/**
	 * 训练计划解析
	 * @param rowData
	 * @param userId
	 * @param cover
	 * @return
	 */
	private TrainPlan parseTrainPlan(List<String> rowData, String userId, Boolean cover,
			int rowIndex, Map<String, TrainPlan> trainPlanMap) {

		// 数据校验
		String planName = rowData.get(CommonConstant.ZERO);
		if (StringUtils.isBlank(planName)) {
			log.error("planName :[{}] is blank", planName);
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_NAME_IS_NULL, rowIndex);
		}

		TrainPlan trainPlan = trainPlanMap.get(planName);
		if (null != (trainPlan)) {
			return trainPlan;
		}

		trainPlan = trainPlanMapper.selectByName(planName, userId);
		if (null != trainPlan) {
			trainPlanMap.put(planName, trainPlan);
			return trainPlan;
		}

		trainPlan = new TrainPlan();
		trainPlan.setResourceId(IDGeneratorUtil.getID());
		trainPlan.setName(planName);
		trainPlan.setCreateUserId(userId);
		trainPlanMap.put(planName, trainPlan);

		return trainPlan;
	}

	/**
	 * 用户转换为聊天用户格式
	 * @param userInfo
	 * @param roleName
	 * @return
	 */
	private DashboardUser userToDashUser(UserInfo userInfo, String roleName) {
		DashboardUser dashboardUser = new DashboardUser();
		dashboardUser.setAvatar(userInfo.getAvatarUrl());
		dashboardUser.setName(userInfo.getName());
		dashboardUser.setUsername(userInfo.getUserName());
		dashboardUser.setId(userInfo.getUserId());
		dashboardUser.setRole(roleName);
		dashboardUser.setStatus(CommonConstant.ONLINE);
		dashboardUser.setSign(CommonConstant.EMPTY_STRING);
		return dashboardUser;
	}

	/**
	 * 获取前n名的成绩
	 *
	 * @param evaluates
	 * @return
	 */
	private List<UserNoteScoreBo> statisticTopNum(List<NoteEvaluate> evaluates, List<Note> notes,
			int num) {

		if (CollectionUtils.isEmpty(notes) || CollectionUtils.isEmpty(evaluates)) {
			return Lists.newArrayList();
		}

		Map<String, String> noteUserMap = StreamApiUtils.toMap(notes, Note::getResourceId,
				note -> note.getUserId());

		// 用户数据查询
		List<String> noteUserIds = StreamApiUtils.map(notes, Note::getUserId);
		Map<String, UserInfo> nameMap = remoteUserService.getIdUserInfoMap(noteUserIds);

		if (num > evaluates.size()) {
			num = evaluates.size();
		}
		return evaluates.stream().sorted(Comparator.comparing(NoteEvaluate::getScore).reversed())
				.limit(num).map(noteEvaluate -> {
					UserNoteScoreBo noteScoreBo = new UserNoteScoreBo();
					noteScoreBo.setScore(noteEvaluate.getScore());

					String userId = noteUserMap.get(noteEvaluate.getNoteId());
					UserInfo userInfo = nameMap.get(userId);
					if (null != userInfo) {
						noteScoreBo.setUserId(userInfo.getUserId());
						noteScoreBo.setName(userInfo.getName());
						noteScoreBo.setUserName(userInfo.getUserName());
					}
					return noteScoreBo;
				}).collect(Collectors.toList());
	}

	/**
	 * 统计签到数据
	 *
	 * @param classroom
	 * @return
	 */
	private SignInfoBo statisticSignInfo(Classroom classroom, List<String> orgUserIds,
			List<NoteEvaluate> evaluates, List<ClassroomSign> signIns) {

		SignInfoBo signInfoBo = new SignInfoBo();

		// 签到数量
		signInfoBo.setIsSign(signIns.size());
		signInfoBo.setNotSign(orgUserIds.size() - signIns.size());

		// 完成数量
		List<NoteEvaluate> passedList = StreamApiUtils.filter(evaluates, NoteEvaluate::getPassed);
		signInfoBo.setFinishCount(passedList.size());

		// 组织信息
		String orgName = remoteOrganizationService.orgNameById(classroom.getOrganizationId());
		signInfoBo.setOrganizationInfo(orgName);

		return signInfoBo;
	}

	/**
	 * 成绩分布数据
	 *
	 * @param evaluates
	 * @return
	 */
	private GradeDistributionBo statisticGrade(List<NoteEvaluate> evaluates) {

		// 空数据返回全为零的对象
		if (CollectionUtils.isEmpty(evaluates)) {
			return new GradeDistributionBo();
		}
		int total = evaluates.size();
		int calculate = CommonConstant.ZERO;

		GradeDistributionBo gradeDistribution = new GradeDistributionBo();

		// 不及格
		List<NoteEvaluate> notPassed = StreamApiUtils.filter(evaluates,
				noteEvaluate -> !noteEvaluate.getPassed());
		gradeDistribution.setNotPass(notPassed.size());
		if ((calculate += notPassed.size()) == total) {
			return gradeDistribution;
		}

		// 及格
		int passed = StreamApiUtils
				.filter(evaluates,
						noteEvaluate -> noteEvaluate.getPassed()
								&& noteEvaluate.getScore() < new Double(CommonConstant.EIGHTY))
				.size();
		gradeDistribution.setPass(passed);
		if ((calculate += passed) == total) {
			return gradeDistribution;
		}

		// 良好
		int good = StreamApiUtils
				.filter(evaluates,
						noteEvaluate -> noteEvaluate.getPassed()
								&& noteEvaluate.getScore() >= new Double(CommonConstant.EIGHTY)
								&& noteEvaluate.getScore() < new Double(CommonConstant.NINETY))
				.size();
		gradeDistribution.setGood(good);
		if ((calculate += good) == total) {
			return gradeDistribution;
		}

		// 优秀
		int excellent = StreamApiUtils
				.filter(evaluates,
						noteEvaluate -> noteEvaluate.getPassed()
								&& noteEvaluate.getScore() >= new Double(CommonConstant.NINETY)
								&& noteEvaluate.getScore() < new Double(CommonConstant.HUNDRED))
				.size();
		gradeDistribution.setExcellent(excellent);
		if ((calculate += excellent) == total) {
			return gradeDistribution;
		}

		// 满分
		int full = StreamApiUtils
				.filter(evaluates, noteEvaluate -> noteEvaluate.getPassed()
						&& noteEvaluate.getScore().equals(new Double(CommonConstant.HUNDRED)))
				.size();
		gradeDistribution.setFullScore(full);
		return gradeDistribution;
	}

	/**
	 * 用户数据转换
	 *
	 * @param userInfo
	 * @param courseLesson
	 * @param orgMap
	 * @return
	 */
	private ClassroomReportBo userToReportBo(UserInfo userInfo, CourseLesson courseLesson,
			Map<String, String> orgMap, Map<String, UserInfo> evaluatorMap) {

		// 用户信息
		ClassroomReportBo reportBo = BeanUtils.copy(userInfo, ClassroomReportBo.class);
		reportBo.setOrganizationName(orgMap.get(userInfo.getOrganizationId()));

		// 实验报告数据
		NoteUniqueContext uniqueContext = NoteUniqueContext.builder()
				.businessId(courseLesson.getResourceId())
				.businessType(Note.NoteResourceType.LESSON.getCode())
				.noteType(Note.NoteType.CLASS_REPORT.getCode()).userId(userInfo.getUserId())
				.build();
		NoteBo noteBo = noteService.selectByUnique(uniqueContext);
		if (null != noteBo && noteBo.getIsSubmit() != null && noteBo.getIsSubmit()) {
			reportBo.setContent(noteBo.getContent());
			reportBo.setSubmitTime(noteBo.getUpdateTime());
			reportBo.setNoteId(noteBo.getResourceId());
		}

		// 实验报告评分数据
		if (null != noteBo) {
			NoteEvaluate noteEvaluate = noteEvaluateMapper.selectByNote(noteBo.getResourceId());
			if (null != noteEvaluate) {
				// 评估数据设置
				reportBo.setEvaluateComment(noteEvaluate.getEvaluateComment());
				reportBo.setEvaluateTime(noteEvaluate.getEvaluateTime());
				reportBo.setScore(noteEvaluate.getScore());
				reportBo.setPassed(noteEvaluate.getPassed());

				// 评估人数据设置
				UserInfo evaluator = evaluatorMap.get(noteEvaluate.getEvaluatorId());
				if (null == evaluator) {
					evaluator = remoteUserService.getById(noteEvaluate.getEvaluatorId());
					evaluatorMap.put(noteEvaluate.getEvaluatorId(), evaluator);
				}

				if (null != evaluator) {
					reportBo.setEvaluatorId(noteEvaluate.getEvaluatorId());
					reportBo.setEvaluatorName(evaluator.getName());
					reportBo.setEvaluatorUserName(evaluator.getUserName());
				}
			}
		}
		return reportBo;
	}

	/**
	 * 用户信息转换为监控信息
	 *
	 * @param userInfo
	 * @return
	 */
	private ClassroomMonitorBo userToMonitorBo(UserInfo userInfo,
			Map<String, ClassroomSign> userSignMap) {

		ClassroomMonitorBo monitorBo = BeanUtils.copy(userInfo, ClassroomMonitorBo.class);

		// 签到数据
		ClassroomSign classroomSign = userSignMap.get(userInfo.getUserId());
		monitorBo.setSignIn(null != classroomSign);
		monitorBo.setNeedHelp(
				null != classroomSign ? StringUtils.isNotBlank(classroomSign.getNodeId()) : false);
		monitorBo.setSignInTime(null != classroomSign ? classroomSign.getSignInTime() : null);

		return monitorBo;
	}

	/**
	 * 实体类转换为bo
	 * @param classroom
	 * @return
	 */
	private ClassroomBo entityToBo(Classroom classroom) {

		ClassroomBo classroomBo = BeanUtils.copy(classroom, ClassroomBo.class);

		// 课程数据组装
		Course course = courseMapper.selectById(classroom.getCourseId());
		if (course == null) {
			log.error("Course not found! course_id=[{}]", classroom.getCourseId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		classroomBo.setCourseName(course.getName());
		classroomBo.setCourseType(course.getCourseType());

		// 课时数据组装
		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		if (lesson == null) {
			log.error("Lesson not found! lesson_id=[{}]", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		classroomBo.setLessonName(lesson.getName());
		classroomBo.setLessonType(lesson.getType());

		// 组织信息
		String orgName = remoteOrganizationService.orgNameById(classroom.getOrganizationId());
		classroomBo.setOrganizationName(orgName);

		String orgPath = remoteOrganizationService.getOrgPath(classroom.getOrganizationId());
		classroomBo.setOrgPath(orgPath);

		List<String> parentIds = remoteOrganizationService
				.getParentIds(classroom.getOrganizationId());
		classroomBo.setParentOrgIds(parentIds);

		// 计划数据
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			TrainPlan trainPlan = trainPlanMapper.selectById(classroom.getTrainPlanId());
			classroomBo.setTrainPlanName(trainPlan.getName());
		}

		// 日程类型
		classroomBo.setClassify(ClassroomType.CLASS.getCode());
		return classroomBo;
	}


	private List<ClassroomBo> filterContinuous(List<Classroom> classrooms) {
		List<ClassroomBo> res = Lists.newArrayList();
		if (classrooms.isEmpty()) {
			return res;
		}
		List<String> planIds = classrooms.stream()
				.filter(c -> c.getTrainPlanId() != null && c.getTrainPlanId().isEmpty())
				.map(Classroom::getTrainPlanId).collect(Collectors.toList());
		if (planIds.isEmpty()) {
			return StreamApiUtils.map(classrooms, this::entityToBo);
		}
		List<TrainPlan> planList = trainPlanMapper.selectBatchIds(planIds);
		List<String> continuousPlanIds = planList.stream()
				.filter(p -> TrainPlan.PlanType.CONTINUOUS.equals(p.getType()))
				.map(TrainPlan::getResourceId).collect(Collectors.toList());
		if (continuousPlanIds.isEmpty()) {
			return StreamApiUtils.map(classrooms, this::entityToBo);
		}
		List<Classroom> classList = classroomMapper
				.selectByPlanIdsOrderTime(StreamApiUtils.map(planList, TrainPlan::getResourceId));
		List<String> lessonIds = StreamApiUtils.map(classList, Classroom::getLessonId);
		String userId = UserInfoContext.getUserContext().getUserId();
		Map<String, List<Classroom>> classMap = Maps.newHashMap();
		for (Classroom classroom : classList) {
			classMap.putIfAbsent(classroom.getTrainPlanId(), Lists.newArrayList());
			classMap.get(classroom.getTrainPlanId()).add(classroom);
		}
		List<CourseLesson> lessonList = courseLessonMapper.selectBatchIds(lessonIds);
		Map<String, CourseLesson> lessonMap = StreamApiUtils.toMap(lessonList,
				CourseLesson::getResourceId, l -> l);
		List<LessonRecord> recordList = lessonRecordMapper.listByLessonAndUser(userId, lessonIds);
		Map<String, LessonRecord> recordMap = StreamApiUtils.toMap(recordList,
				LessonRecord::getLessonId, lessonRecord -> lessonRecord);
		List<Note> reportList = noteMapper.selectSubmittedByUserAndType(userId,
				Note.NoteType.CLASS_REPORT.getCode(), Note.NoteResourceType.LESSON.getCode(),
				lessonIds);
		Map<String, Note> reportMap = StreamApiUtils.toMap(reportList, Note::getBusinessId, n -> n);

		Set<String> inaccessible = Sets.newHashSet();
		for (String planId : continuousPlanIds) {
			List<Classroom> classroomList = classMap.get(planId);
			int i;
			for (i = 0; i < classroomList.size(); i++) {
				CourseLesson lesson = lessonMap.get(classroomList.get(i).getLessonId());
				if (lesson.isTheory()) {
					if (!recordMap.containsKey(lesson.getResourceId())) {
						break;
					}
				} else if (lesson.isExp()) {
					if (!reportMap.containsKey(lesson.getResourceId())) {
						break;
					}
				}
			}
			for (i = i + 1; i < classroomList.size(); i++) {
				inaccessible.add(classroomList.get(i).getResourceId());
			}
		}
		
		List<Classroom> filtered = StreamApiUtils.filter(classrooms,
				c -> !inaccessible.contains(c.getResourceId()));
		List<Course> courseList = courseMapper
				.selectBatchIds(StreamApiUtils.map(filtered, Classroom::getCourseId));
		Map<String, Course> courseMap = StreamApiUtils.toMap(courseList, Course::getResourceId,
				c -> c);
		Map<String, TrainPlan> planMap = StreamApiUtils.toMap(planList, TrainPlan::getResourceId,
				p -> p);

		List<String> orgIdList = filtered.stream().map(Classroom::getOrganizationId).distinct().collect(Collectors.toList());
		Map<String, String> orgNameMap = Maps.newHashMap();
		Map<String, String> orgPathMap = Maps.newHashMap();
		Map<String, List<String>> parentIdsMap = Maps.newHashMap();
		for (String orgId : orgIdList) {
			orgNameMap.put(orgId, remoteOrganizationService.orgNameById(orgId));
			orgPathMap.put(orgId, remoteOrganizationService.getOrgPath(orgId));
			parentIdsMap.put(orgId, remoteOrganizationService.getParentIds(orgId));
		}

		for (Classroom classroom : filtered) {
			ClassroomBo classroomBo = BeanUtils.copy(classroom, ClassroomBo.class);

			// 课程数据组装
			Course course = courseMap.get(classroom.getCourseId());
			if (course == null) {
				log.error("Course not found! course_id=[{}]", classroom.getCourseId());
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			classroomBo.setCourseName(course.getName());
			classroomBo.setCourseType(course.getCourseType());

			// 课时数据组装
			CourseLesson lesson = lessonMap.get(classroom.getLessonId());
			if (lesson == null) {
				log.error("Lesson not found! lesson_id=[{}]", classroom.getLessonId());
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			classroomBo.setLessonName(lesson.getName());
			classroomBo.setLessonType(lesson.getType());

			// 组织信息
			classroomBo.setOrganizationName(orgNameMap.get(classroom.getOrganizationId()));

			classroomBo.setOrgPath(orgPathMap.get(classroom.getOrganizationId()));

			classroomBo.setParentOrgIds(parentIdsMap.get(classroom.getOrganizationId()));

			// 计划数据
			if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
				TrainPlan trainPlan = planMap.get(classroom.getTrainPlanId());
				if (null != trainPlan) {
					classroomBo.setTrainPlanName(trainPlan.getName());
				}
			}

			// 日程类型
			classroomBo.setClassify(ClassroomType.CLASS.getCode());
			res.add(classroomBo);
		}
		return res;
	}

}
