package com.cyberpeace.oj.course.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.*;
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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Override
	public Classroom create(Classroom classroom, AuthContext authContext) {

		// 数据校验
		validateClassroomOperation(classroom, authContext);
		// 组织下是否存在用户 todo

		// 数据新增
		classroom.setResourceId(IDGeneratorUtil.getID());
		classroomMapper.insert(classroom);

		// 生成分组数据 todo
		// 公告发布 todo
		return classroom;
	}

	@Override
	public Integer updateClassroom(Classroom classroom, AuthContext authContext) {
		int result = classroomMapper.updateById(classroom);

		// 重新生成分组 课时改变 组织改变 理论课变为实验课 todo
		return result;
	}

	@Override
	public List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery,
			AuthContext authContext) {

		List<String> authCourseIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(authCourseIds)) {
			return Lists.newArrayList();
		}

		// 查询转换数据
		List<Classroom> classrooms = classroomMapper.selectListByCourse(authCourseIds,
				roomListQuery);
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

	@Override
	public List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext) {

		List<Classroom> classrooms = classroomMapper.selectList(roomListQuery);

		return StreamApiUtils.map(classrooms, classroom -> entityToBo(classroom));
	}

	@Override
	public ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId) {

		// 基本数据校验
		Classroom classroom = validateRoom(classroomId);

		// 查询组织下用户
		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId());

		List<ClassroomSign> signList = classroomSignMapper.selectByRoom(classroomId);
		Map<String, ClassroomSign> signMap = StreamApiUtils.toMap(signList,
				ClassroomSign::getUserId, classroomSign -> classroomSign);

		List<ClassroomMonitorBo> monitorBos = StreamApiUtils.map(userInfos,
				userInfo -> userToMonitorBo(userInfo, signMap));

		return PageUtil.getListResult(monitorBos, page, monitorBos.size());

	}

	@Override
	public List<ClassroomReportBo> selectClassroomReport(String classroomId) {
		// 基本数据校验
		Classroom classroom = validateRoom(classroomId);
		CourseLesson courseLesson = validateLesson(classroom);

		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId());
		Map<String, String> orgMap = remoteOrganizationService
				.childrenMap(classroom.getOrganizationId(), true);

		return StreamApiUtils.map(userInfos,
				userInfo -> userToReportBo(userInfo, courseLesson, orgMap));
	}

	@Override
	public ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext) {

		// 基本数据校验
		Classroom classroom = validateRoom(classroomId);
		CourseLesson courseLesson = validateLesson(classroom);

		// 查询组织用户
		List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId());
		List<String> orgUserIds = StreamApiUtils.map(userInfos, UserInfo::getUserId);

		// 查询所有笔记
		NoteListQuery listQuery = NoteListQuery.builder().noteType(Note.NoteType.REPORT.getCode())
				.businessId(courseLesson.getResourceId())
				.businessType(Note.NoteResourceType.LESSON.getCode()).userIds(orgUserIds).build();
		List<Note> notes = noteMapper.selectByListQuery(listQuery, authContext.getIsWeb());

		// 评估数据查询
		List<String> noteIds = StreamApiUtils.map(notes, Note::getResourceId);
		List<NoteEvaluate> evaluates = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(noteIds)) {
			List<NoteEvaluate> evaluateList = noteEvaluateMapper.selectBatchNoteIds(noteIds,
					authContext.getIsWeb());
			evaluates.addAll(evaluateList);
		}

		// 签到信息统计
		SignInfoBo signInfoBo = statisticSignInfo(classroom, orgUserIds, evaluates);

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
		validateRoom(classroomId);

		// 分组数据查询
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoMapper.selectByRoom(classroomId);
		List<String> groupInfoIds = StreamApiUtils.map(groupInfoList,
				ClassroomGroupInfo::getResourceId);

		if (CollectionUtils.isEmpty(groupInfoIds)) {
			return classroomMapper.deleteById(classroomId);
		}

		// 查询已启动已启动场景数据
		List<OjLocalScene> ojLocalScenes = StreamApiUtils.map(groupInfoIds,
				groupId -> ojLocalSceneMapper.selectGroupScene(groupId));
		List<String> ojLocalSceneIds = StreamApiUtils.map(ojLocalScenes,
				OjLocalScene::getResourceId);
		List<String> localSceneIds = StreamApiUtils.map(ojLocalScenes,
				OjLocalScene::getLocalSceneId);

		// 远程场景数据删除
		if (CollectionUtils.isNotEmpty(localSceneIds)) {
			List<LocalScene> localScenes = localSceneMapper.selectBatchIds(localSceneIds);
			localScenes.forEach(localScene -> localSceneService.deleteRemoteScene(localScene));
		}

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				// 场景数据删除
				if (CollectionUtils.isNotEmpty(ojLocalSceneIds)) {
					ojLocalSceneMapper.deleteBatchIds(ojLocalSceneIds);
				}
				if (CollectionUtils.isNotEmpty(localSceneIds)) {
					localSceneMapper.deleteBatchIds(localSceneIds);
				}

				// 课堂数据删除
				classroomGroupInfoMapper.deleteBatchIds(groupInfoIds);
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
			List<NoteEvaluate> evaluates) {

		SignInfoBo signInfoBo = new SignInfoBo();

		// 签到数量
		List<ClassroomSign> signList = classroomSignMapper.selectByRoom(classroom.getResourceId());
		List<ClassroomSign> signIns = StreamApiUtils.filter(signList, ClassroomSign::getSignIn);
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
		monitorBo.setNeedHelp(null != classroomSign ? classroomSign.getNeedHelp() : null);
		monitorBo.setSignInTime(null != classroomSign ? classroomSign.getSignInTime() : null);

		return monitorBo;
	}

	/**
	 * 实体类转换为bo
	 *
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
		return classroomBo;
	}

	/**
	 * 课堂数据校验
	 *
	 * @param classroomId
	 * @return
	 */
	private Classroom validateRoom(String classroomId) {
		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.error("Classroom is not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return classroom;
	}

	/**
	 * 课程数据校验
	 *
	 * @param classroom
	 * @return
	 */
	private CourseLesson validateLesson(Classroom classroom) {

		CourseLesson courseLesson = courseLessonMapper.selectById(classroom.getLessonId());
		if (null == courseLesson) {
			log.error("CourseLesson not found! lesson_id=[{}]", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return courseLesson;
	}

	/**
	 * 校验操作权限 课程课时
	 * @param classroom
	 */
	private void validateClassroomOperation(Classroom classroom, AuthContext authContext) {
		// 课时数据校验
		List<String> enableCourseIds = courseMapper.selectByAuth(authContext);

		if (CollectionUtils.isEmpty(enableCourseIds)
				|| !enableCourseIds.contains(classroom.getCourseId())) {
			log.error("Course not permission! course_id=[{}]", classroom.getCourseId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// 课时数据校验
		validateLesson(classroom);
	}

}
