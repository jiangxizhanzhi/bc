package com.cyberpeace.oj.course.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.GxLocalOrg;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.ClassroomScoreMapper;
import com.cyberpeace.cs.dao.mapper.GxLocalOrgMapper;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
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
	private GxLocalOrgMapper gxLocalOrgMapper;

	@Autowired
	private GxTaskService gxTaskService;

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private ClassroomScoreMapper classroomScoreMapper;

	@Override
	public Classroom create(Classroom classroom, AuthContext authContext) {

		// 数据校验
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext, false);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());
		classroomValidateHandler.validateClassroomLessonExist(classroom.getLessonId(),
				classroom.getOrganizationId(), null);
		classroomValidateHandler.validateClassroomStartAndEnd(classroom.getStartTime(),
				classroom.getEndTime());
		classroomValidateHandler.validateClassroomTimeOverlap(classroom.getOrganizationId(),
				classroom.getStartTime(), classroom.getEndTime());

		String orgId = classroom.getOrganizationId();

		// 课堂基本数据获取
		List<String> orgIds = classroomValidateHandler.validateClassroomOrgHasUser(orgId);
		classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		classroom.setResourceId(IDGeneratorUtil.getID());

		// 分组数据
		List<String> userIds = userIdsByOrgs(orgIds);
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoService.initClassroomGroupInfo(
				classroom.getResourceId(), courseLesson, userIds, CommonConstant.ONE);

		// 如果课程无访问权限 把组织数据加入
		authContext.setIsWeb(CommonConstant.TRUE);
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);
		if (!authCourseIds.contains(classroom.getCourseId())
				&& gxLocalOrgMapper.selectById(orgId) == null) {
			changeCourseOrg(course, orgIds);
		}

		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(classroom.getOrganizationId());

		// 公告发布 todo

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				classroomMapper.insert(classroom);
				classroomGroupInfoService.saveBatch(groupInfoList);
				courseMapper.updateById(course);

				if (gxLocalOrg != null) {
					gxLocalOrg.setClassRoomId(classroom.getResourceId());
					gxLocalOrgMapper.updateById(gxLocalOrg);
				}
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

			// 先判断有无本地组织
			GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);
			if (gxLocalOrg != null) {
				userIds.addAll(gxTaskService.getLocalUserList(gxLocalOrg));
				continue;
			}

			// 判断远程组织
			List<UserInfo> userInfos = remoteUserService.ListByOrgId(orgId, false);
			List<String> orgUserIds = StreamApiUtils.map(userInfos, UserInfo::getUserId);
			userIds.addAll(orgUserIds);
		}

		return userIds;
	}

	private List<String> getLocalOrgUserIds(String orgId) {
		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);
		if (gxLocalOrg != null) {
			return gxTaskService.getLocalUserList(gxLocalOrg);
		}
		return null;
	}

	@Override
	public List<String> getOrgIds(String orgId) {

		// 首先校验本地组织
		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);

		if (gxLocalOrg != null) {
			List<String> orgIds = Lists.newArrayList(orgId);
			return orgIds;
		}

		// 校验远程组织
		List<String> orgIds = remoteOrganizationService.childrenList(orgId, true);
		return orgIds;
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
			List<String> distinctList = StreamApiUtils.distinct(authOrgIds);
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
		classroomValidateHandler.validateClassroomLessonExist(classroom.getLessonId(),
				classroom.getOrganizationId(), classroom.getResourceId());
		courseValidateHandler.validateCoursePermission(classroom.getCourseId(), authContext, false);
		Course course = courseValidateHandler.validateCourseExist(classroom.getCourseId());
		Classroom existRoom = classroomValidateHandler
				.validateClassroomExist(classroom.getResourceId());
		validateChangeClassroom(classroom);

		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());

		// 组织数据获取
		String orgId = classroom.getOrganizationId();
		List<String> orgIds = classroomValidateHandler.validateClassroomOrgHasUser(orgId);
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
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);
		if (!authCourseIds.contains(classroom.getCourseId())
				&& gxLocalOrgMapper.selectById(orgId) == null) {
			changeCourseOrg(course, orgIds);
		}

		boolean finalEnableChange = enableChange;
		List<ClassroomGroupInfo> finalGroupInfos = groupInfos;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				if (!classroom.getResourceId().equals(existRoom.getResourceId())) {
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
		List<OjLocalScene> localScenes = ojLocalSceneMapper.selectByGroupIds(groupIds);

		if (CollectionUtils.isNotEmpty(localScenes)) {
			log.error("Classroom has scene is running! classroom_id=[{}]",
					classroom.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.CLASS_SCENE_IS_RUNNING);
		}

		// 校验课程表是否和共性平台任务关联
		GxTask gxTask = gxTaskMapper.selectByEventId(classroom.getResourceId());
		if (gxTask != null) {
			throw new BusinessException(OjBusinessErrorCode.CLASSROOM_IS_BOUND_TO_TASK);
		}
	}

	@Override
	public List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery,
			AuthContext authContext) {

		// 权限过滤
		List<String> authCourseIds = courseMapper.selectByAuth(authContext);

		// if (CollectionUtils.isEmpty(authCourseIds)) {
		// return Lists.newArrayList();
		// }

		// 公开过滤
		List<String> publicCourseIds = courseMapper.selectIdsByCategory(authCourseIds, null,
				authContext.getIsWeb());
		if (CollectionUtils.isEmpty(publicCourseIds)) {
			return Lists.newArrayList();
		}

		// 查询转换数据
		List<Classroom> classrooms = classroomMapper.selectListByCourse(authCourseIds,
				roomListQuery);

		// 添加本地组织课程并去重
		classrooms = addLocalOrgClassRoom(classrooms, authContext.getUserId(), roomListQuery);

		List<ClassroomBo> classroomBos = StreamApiUtils.map(classrooms,
				classroom -> entityToBo(classroom));

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
	 * 添加本地组织课程并去重
	 */
	private List<Classroom> addLocalOrgClassRoom(List<Classroom> classrooms, String userId,
			ClassRoomListQuery roomListQuery) {

		List<Classroom> localClassRooms = classroomMapper.selectByUserIdAndNotInCourses(userId,
				null, roomListQuery);
		classrooms.addAll(localClassRooms);

		// 课程去重
		List<String> classroomIds = Lists.newArrayList();
		List<Classroom> finalClassrooms = Lists.newArrayList();

		for (Classroom classroom : classrooms) {
			if (classroomIds.contains(classroom.getResourceId())) {
				continue;
			}
			classroomIds.add(classroom.getResourceId());
			finalClassrooms.add(classroom);
		}

		return finalClassrooms;
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
		List<UserInfo> userInfos = gxTaskService.getUserInfosByOrgId(classroom.getOrganizationId());

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

			JSONArray nodeSessions = remoteSceneService.getNodeSessions(sceneIds, nodeIds, null,
					ClassroomSign.SessionType.ASSISTANCE.getType());
			List<Object> filterList = StreamApiUtils.filter(nodeSessions, node -> StringUtils
					.isNotBlank(((JSONObject) node).getString(JsonKeyConstant.ASSISTANCE_URL)));
			Map<String, String> userAssistanceMap = StreamApiUtils.toMap(filterList,
					object -> ((JSONObject) object).getString(JsonKeyConstant.USER_KEY),
					object -> ((JSONObject) object).getString(JsonKeyConstant.ASSISTANCE_URL));

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

		List<UserInfo> userInfos = gxTaskService.getUserInfosByOrgId(classroom.getOrganizationId());

		List<UserInfo> filterUsers = StreamApiUtils.filter(userInfos,
				userInfo -> StringUtils.isBlank(userName)
						|| userInfo.getUserName().contains(userName)
						|| userInfo.getName().contains(userName));
		Map<String, String> orgMap = getOrgMap(classroom.getOrganizationId());

		return StreamApiUtils.map(filterUsers,
				userInfo -> userToReportBo(userInfo, courseLesson, orgMap));
	}

	private Map<String, String> getOrgMap(String orgId) {

		// 首先校验本地组织
		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);

		Map<String, String> orgMap = Maps.newHashMap();

		if (gxLocalOrg != null) {
			orgMap.put(orgId, gxLocalOrg.getOrgName());
			return orgMap;
		}

		return remoteOrganizationService.childrenMap(orgId, true);
	}

	@Override
	public ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext) {

		// 基本数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		CourseLesson courseLesson = courseValidateHandler
				.validateLessonExist(classroom.getLessonId(), authContext.getIsWeb());

		// 查询组织用户
		List<UserInfo> userInfos = gxTaskService.getUserInfosByOrgId(classroom.getOrganizationId());
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
				gxTaskService.clearClassRoomOrg(classroomId);
				classroomScoreMapper.clearByClassroomId(classroomId);
				return classroomMapper.deleteById(classroomId);
			}
		});
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
		String orgName = getOrgName(classroom.getOrganizationId());
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

		String orgName = orgMap.get(userInfo.getOrganizationId());

		if (orgName == null) {
			orgName = userInfo.getOrganizationData();
		}

		reportBo.setOrganizationName(orgName);

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
		String orgName = getOrgName(classroom.getOrganizationId());
		classroomBo.setOrganizationName(orgName);

		// 如果组织为本地组织，返回用户列表
		if (classroom.getUserIds() != null) {
			classroomBo
					.setUserIds(JsonUtil.toJsonObjectArray(classroom.getUserIds(), String.class));
		}
		return classroomBo;
	}

	private String getOrgName(String orgId) {

		// 首先校验本地组织
		GxLocalOrg gxLocalOrg = gxLocalOrgMapper.selectById(orgId);

		if (gxLocalOrg != null) {
			return gxLocalOrg.getOrgName();
		}

		// 校验远程组织
		return remoteOrganizationService.orgNameById(orgId);
	}

}
