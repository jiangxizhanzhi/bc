package com.cyberpeace.oj.course.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardFriend;
import com.cyberpeace.common.model.room.DashboardGroup;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.ClassroomRole;
import com.cyberpeace.oj.ojbase.constant.ClassroomType;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
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
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

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

	@Override
	public Classroom create(Classroom classroom, AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());
		classroomValidateHandler.validateClassroomLessonExist(classroom.getLessonId(),
				classroom.getOrganizationId());
		classroomValidateHandler.validateClassroomStartAndEnd(classroom.getStartTime(),
				classroom.getEndTime());
		classroomValidateHandler.validateClassroomTimeOverlap(classroom.getOrganizationId(),
				classroom.getStartTime(), classroom.getEndTime());
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			classroomValidateHandler.validatePlanExistById(classroom.getTrainPlanId());
		}

		// 课堂基本数据获取
		List<String> orgIds = classroomValidateHandler
				.validateClassroomOrgHasUser(classroom.getOrganizationId());
		classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		classroom.setResourceId(IDGeneratorUtil.getID());

		// 分组数据
		List<String> userIds = userIdsByOrgs(orgIds);
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoService.initClassroomGroupInfo(
				classroom.getResourceId(), courseLesson, userIds, CommonConstant.ONE);

		// 如果课程无访问权限 把组织数据加入
		if (course.getReadAuthType().intValue() == AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode()
				&& !course.getReadAuthIds().contains(classroom.getOrganizationId())) {
			changeCourseOrg(course, orgIds);
		}

		// 公告发布 todo

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				classroomMapper.insert(classroom);
				classroomGroupInfoService.saveBatch(groupInfoList);
				courseMapper.updateById(course);
			}
		});
		return classroom;
	}

	/**
	 * 获取用户编号列表
	 * @param orgIds
	 * @return
	 */
	private List<String> userIdsByOrgs(List<String> orgIds) {

		List<String> userIds = Lists.newArrayList();
		for (String orgId : orgIds) {
			List<UserInfo> userInfos = remoteUserService.ListByOrgId(orgId, false);
			List<String> orgUserIds = StreamApiUtils.map(userInfos, UserInfo::getUserId);
			userIds.addAll(orgUserIds);
		}

		return userIds;
	}

	/**
	 * 修改课程可访问组织数据
	 * @param course
	 * @param orgIds
	 */
	private void changeCourseOrg(Course course, List<String> orgIds) {

		if (StringUtils.isNotBlank(course.getReadAuthIds())) {
			List<String> authOrgIds = JsonUtil.toJsonObjectArray(course.getReadAuthIds(),
					String.class);
			orgIds.addAll(authOrgIds);
			List<String> distinctList = StreamApiUtils.distinct(orgIds);
			course.setReadAuthIds(JsonUtil.arrayToString(distinctList));
		}
	}

	@Override
	public Boolean updateClassroom(Classroom classroom, AuthContext authContext) {

		// 数据校验
		classroomValidateHandler.validateClassroomStartAndEnd(classroom.getStartTime(),
				classroom.getEndTime());
		classroomValidateHandler.validateUpdateClassroomTimeOverlap(classroom.getResourceId(),
				classroom.getOrganizationId(), classroom.getStartTime(), classroom.getEndTime());
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		Classroom existRoom = classroomValidateHandler
				.validateClassroomExist(classroom.getResourceId());
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			classroomValidateHandler.validatePlanExistById(classroom.getTrainPlanId());
		}
		validateChangeClassroom(classroom);

		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());

		// 组织数据获取
		List<String> orgIds = remoteOrganizationService.childrenList(classroom.getOrganizationId(),
				true);
		classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));

		boolean enableChange = !existRoom.getLessonId().equals(classroom.getLessonId());
		enableChange |= !existRoom.getOrganizationId().equals(classroom.getOrganizationId());
		enableChange &= courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
				.getCode().intValue();

		// 分组数据
		List<ClassroomGroupInfo> groupInfos = null;
		if (enableChange) {
			List<String> userIdsByOrgs = userIdsByOrgs(orgIds);
			groupInfos = classroomGroupInfoService.initClassroomGroupInfo(classroom.getResourceId(),
					courseLesson, userIdsByOrgs, CommonConstant.ONE);
		}

		// 课程修改
		if (course.getReadAuthType().intValue() == AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode()
				&& !course.getReadAuthIds().contains(classroom.getOrganizationId())) {
			changeCourseOrg(course, orgIds);
		}

		boolean finalEnableChange = enableChange;
		List<ClassroomGroupInfo> finalGroupInfos = groupInfos;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				if (!classroom.getOrganizationId().equals(existRoom.getOrganizationId())) {
					courseMapper.updateById(course);
				}
				classroomMapper.updateById(classroom);
				if (finalEnableChange) {
					classroomGroupInfoMapper.deleteByRoom(classroom.getResourceId());
					if (CollectionUtils.isNotEmpty(finalGroupInfos)) {
						classroomGroupInfoService.saveBatch(finalGroupInfos);
					}
				}
			}
		});

		return CommonConstant.TRUE;

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

		// 权限过滤
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(authCourseIds)) {
			return Lists.newArrayList();
		}

		// 公开过滤
		List<String> publicCourseIds = courseMapper.selectIdsByCategory(authCourseIds, null,
				authContext.getIsWeb());
		if (CollectionUtils.isEmpty(publicCourseIds)) {
			return Lists.newArrayList();
		}

		// 课程表数据查询
		List<Classroom> classrooms = classroomMapper.selectListByCourse(authCourseIds,
				roomListQuery);
		List<ClassroomBo> classroomBos = StreamApiUtils.map(classrooms,
				classroom -> entityToBo(classroom));

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
		Map<String, List<ClassroomBo>> startTimeMap = StreamApiUtils.groupBy(classroomBos,
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

		return StreamApiUtils.map(classrooms, classroom -> entityToBo(classroom));
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

		// 页面获取
		List<String> nodeIds = StreamApiUtils.map(signList, ClassroomSign::getNodeId);
		if (CollectionUtils.isNotEmpty(nodeIds)) {
			List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoMapper
					.selectByRoom(classroomId);
			List<String> sceneIds = StreamApiUtils.map(groupInfoList, groupInfo -> {
				OjLocalScene ojLocalScene = ojLocalSceneMapper
						.selectGroupScene(groupInfo.getResourceId());
				if (null == ojLocalScene) {
					return null;
				}

				LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
				return localScene.getMpSceneId();
			});

			Map<String, String> userAssistanceMap = remoteSceneService.getAssistanceMap(sceneIds,
					nodeIds, null);
			monitorBos.forEach(classroomMonitorBo -> classroomMonitorBo
					.setHelpUrl(classroomMonitorBo.getNeedHelp()
							? userAssistanceMap.get(classroomMonitorBo.getUserId())
							: null));

		}

		return PageUtil.getListResult(monitorBos, page, monitorBos.size());

	}

	@Override
	public List<ClassroomReportBo> selectClassroomReport(String classroomId, String userName) {

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
		Map<String, String> orgMap = remoteOrganizationService
				.childrenMap(classroom.getOrganizationId(), true);

		return StreamApiUtils.map(filterUsers,
				userInfo -> userToReportBo(userInfo, courseLesson, orgMap));
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
		List<ClassroomSign> signIns = StreamApiUtils.filter(signList, ClassroomSign::getSignIn);
		List<String> signUsers = StreamApiUtils.map(signIns, ClassroomSign::getUserId);
		// 查询所有笔记
		List<Note> notes = new ArrayList<>();
		// 仅统计签到过的用户
		if (signUsers.size() != 0) {
			NoteListQuery listQuery = NoteListQuery.builder()
					.noteType(Note.NoteType.REPORT.getCode())
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
		ClassroomStatisticBo statisticBo = ClassroomStatisticBo.builder().signInfo(signInfoBo)
				.top20(topNum).gradeDistribution(distributionBo).build();
		return statisticBo;
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

		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoMapper.selectByRoom(roomId);
		List<String> userIds = StreamApiUtils.distinctFlatMap(groupInfoList,
				groupInfo -> JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class));
		if (CollectionUtils.isEmpty(userIds)) {
			log.error("Classroom user is null! classroom_id=[{}]", roomId);
			return null;
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
	public List<Classroom> importTrainPlan(String realPath, UserInfo userInfo, boolean cover) {

		// excel数据读取
		ExcelDataHandler dataHandler = new ExcelDataHandler(realPath, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);

		// 数据解析
		List<Classroom> classrooms = Lists.newArrayList();
		Map<String, TrainPlan> trainPlanMap = Maps.newHashMap();

		for (int i = CommonConstant.ONE; i <= sheetData.size(); i++) {

			// 空数据不管
			if (!validateEmptyData(sheetData.get(i - CommonConstant.ONE), i)) {
				continue;
			}

			// 解析行
			TrainPlan trainPlan = parseTrainPlan(sheetData.get(i - CommonConstant.ONE),
					userInfo.getUserId(), cover, i, trainPlanMap);

			Classroom classroom = parseClassroom(sheetData.get(i - CommonConstant.ONE), userInfo,
					i);
			Classroom existClassRoom = classroomMapper
					.selectByOrgAndLesson(classroom.getOrganizationId(), classroom.getLessonId());
			if (null != existClassRoom && (StringUtils.isBlank(existClassRoom.getTrainPlanId())
					|| !existClassRoom.getTrainPlanId().equals(trainPlan.getResourceId()))) {
				throw new BusinessException(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME);
			}
			classroom.setTrainPlanId(trainPlan.getResourceId());
			classrooms.add(classroom);
		}

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
			}
		});

		return classrooms;
	}

	@Override
	public void exportTrainPlan(UserInfo userInfo, String filePath) {
		// 数据查询及转换
		List<TrainPlan> trainPlanList = trainPlanMapper.selectByUser(userInfo.getUserId());
		List<TrainPlanExportBo> exportBoList = StreamApiUtils.flatMap(trainPlanList,
				trainPlan -> planToExportBo(trainPlan));

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAIN_PLAN);
	}

	/** 数据转换*/
	private List<TrainPlanExportBo> planToExportBo(TrainPlan trainPlan) {
		List<Classroom> classroomList = classroomMapper.selectByPlan(trainPlan.getResourceId());
		return StreamApiUtils.map(classroomList,
				classroom -> classRoomToExportBo(trainPlan.getName(), classroom));
	}

	/** 数据转换*/
	private TrainPlanExportBo classRoomToExportBo(String planName, Classroom classroom) {
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
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		planExportBo.setCourseName(null != course ? course.getName() : CommonConstant.EMPTY_STRING);
		planExportBo.setLessonName(null != lesson ? lesson.getName() : CommonConstant.EMPTY_STRING);

		planExportBo.setStartTime(classroom.getStartTime());
		planExportBo.setEndTime(classroom.getEndTime());

		String orgPath = remoteOrganizationService.getOrgPath(classroom.getOrganizationId());
		if (StringUtils.isBlank(orgPath)) {
			log.error("Classroom organization is not found! classroom_id=[{}], org_id=[{}]",
					classroom.getResourceId(), classroom.getOrganizationId());
			throw new BusinessException(OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL);
		}
		planExportBo.setOrgPath(orgPath);

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
		if (!cover && null != trainPlan) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_EXIST);
		}

		if (null == trainPlan) {
			return Lists.newArrayList();
		}

		List<Classroom> classroomList = classroomMapper.selectByPlan(trainPlan.getResourceId());
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
	private Classroom parseClassroom(List<String> roomData, UserInfo userInfo, int rowIndex) {
		String courseName;
		if (StringUtils.isBlank(courseName = roomData.get(CommonConstant.ONE))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_COURSE_IS_NULL, rowIndex);
		}

		String courseId;
		if (StringUtils.isBlank(courseId = courseMapper.selectByName(courseName))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_COURSE_NOT_EXIST, rowIndex);
		}

		String lessonName;
		if (StringUtils.isBlank(lessonName = roomData.get(CommonConstant.TWO))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_IS_NULL, rowIndex);
		}

		String lessonId;
		if (StringUtils.isBlank(
				lessonId = courseLessonMapper.selectByCourseAndName(courseId, lessonName))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_NOT_EXIST, rowIndex);
		}

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (!courseLesson.getIsPublic()) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_LESSON_NOT_PUBLIC, rowIndex);
		}

		String startTime;
		String endTime;
		if (StringUtils.isBlank(startTime = roomData.get(CommonConstant.THREE))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_TIME_IS_NULL, rowIndex);
		}
		if (StringUtils.isBlank(endTime = roomData.get(CommonConstant.FOUR))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_TIME_IS_NULL, rowIndex);
		}
		if (TimeUtil.toDate1(endTime).before(TimeUtil.toDate1(startTime))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_END_GT_START_TIME, rowIndex);
		}

		String orgTree;
		if (StringUtils.isBlank(orgTree = roomData.get(CommonConstant.FIVE))) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_ORG_IS_NULL, rowIndex);
		}
		// 组织数据获取
		String orgId = remoteOrganizationService.getOrgIdByTree(orgTree);
		if (StringUtils.isBlank(orgId)) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_ORG_NOT_EXIST, rowIndex);
		}

		// 基本数据填充
		Classroom classroom = new Classroom();
		classroom.setCreateUserId(userInfo.getUserId());
		classroom.setResourceId(IDGeneratorUtil.getID());
		classroom.setCourseId(courseId);
		classroom.setLessonId(lessonId);
		classroom.setStartTime(TimeUtil.toDate1(startTime));
		classroom.setEndTime(TimeUtil.toDate1(endTime));

		classroom.setOrganizationId(orgId);
		List<String> childrenList = remoteOrganizationService.childrenList(orgId, true);
		classroom.setOrganizationIds(JsonUtil.arrayToString(childrenList));

		return classroom;
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
		String planName;
		if (StringUtils.isBlank(planName = rowData.get(CommonConstant.ZERO))) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_NAME_IS_NULL, rowIndex);
		}

		TrainPlan trainPlan;
		if (null != (trainPlan = trainPlanMap.get(planName))) {
			return trainPlan;
		}

		trainPlan = trainPlanMapper.selectByName(planName, userId);

		// 训练计划是否可覆盖 任务是否存在开始的
		if (null != trainPlan) {
			classroomValidateHandler.validatePlanCover(trainPlan.getResourceId(),
					trainPlan.getName());
		}

		if (null != trainPlan && !cover) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_EXIST, rowIndex);
		}

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
		dashboardUser.setUsername(userInfo.getName());
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
				noteEvaluate -> !noteEvaluate.getPassed()
						|| noteEvaluate.getScore() < new Double(CommonConstant.SIXTY));
		gradeDistribution.setNotPass(notPassed.size());
		if ((calculate += notPassed.size()) == total) {
			return gradeDistribution;
		}

		// 及格
		int passed = StreamApiUtils
				.filter(evaluates,
						noteEvaluate -> noteEvaluate.getPassed()
								&& noteEvaluate.getScore() >= new Double(CommonConstant.SIXTY)
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
			Map<String, String> orgMap) {

		// 用户信息
		ClassroomReportBo reportBo = BeanUtils.copy(userInfo, ClassroomReportBo.class);
		reportBo.setOrganizationName(orgMap.get(userInfo.getOrganizationId()));

		// 实验报告数据
		NoteUniqueContext uniqueContext = NoteUniqueContext.builder()
				.businessId(courseLesson.getResourceId())
				.businessType(Note.NoteResourceType.LESSON.getCode())
				.noteType(Note.NoteType.REPORT.getCode()).userId(userInfo.getUserId()).build();
		NoteBo noteBo = noteService.selectByUnique(uniqueContext);
		if (null != noteBo) {
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
				UserInfo evaluator = remoteUserService.getById(noteEvaluate.getEvaluatorId());
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
		// 组织信息查询
		String orgName = remoteOrganizationService.orgNameById(userInfo.getOrganizationId());
		monitorBo.setOrganizationName(orgName);

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

		// 计划数据
		if (StringUtils.isNotBlank(classroom.getTrainPlanId())) {
			TrainPlan trainPlan = trainPlanMapper.selectById(classroom.getTrainPlanId());
			classroomBo.setTrainPlanName(trainPlan.getName());
		}

		// 日程类型
		classroomBo.setClassify(ClassroomType.CLASS.getCode());
		return classroomBo;
	}

}
