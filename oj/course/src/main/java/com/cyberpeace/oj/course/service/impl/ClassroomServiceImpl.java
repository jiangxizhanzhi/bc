package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.ClassroomRole;
import com.cyberpeace.oj.ojbase.constant.ClassroomType;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.dao.context.NoteListQuery;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteEvaluateMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课堂Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

	/**
	 * 课堂Mapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 远程组织Service
	 */
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课堂标志Mapper
	 */
	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	/**
	 * 注意评估Mapper
	 */
	@Autowired
	private NoteEvaluateMapper noteEvaluateMapper;

	/**
	 * 注意Mapper
	 */
	@Autowired
	private NoteMapper noteMapper;

	/**
	 * 课堂群信息Mapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * OJ当地场景Mapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;


	/**
	 * 课堂验证处理器
	 */
	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	/**
	 * 课程验证处理器
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 纸Mapper
	 */
	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 课纸评估Mapper
	 */
	@Autowired
	private LessonPaperEvaluateMapper lessonPaperEvaluateMapper;

	/**
	 * 实践理论裁判Service
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 实践实操裁判Service
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 训练计划Mapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

	/**
	 * 远程竞赛Service
	 */
	@Autowired
	private RemoteAdService remoteAdService;

	/**
	 * 判断是否需要修改课程
	 *
	 * @param orgId  组织id
	 * @param course 课程
	 * @return boolean
	 */
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

	/**
	 * 判断是否修改群组
	 *
	 * @param current      当前
	 * @param update       更新
	 * @param courseLesson 课程课程
	 * @return boolean
	 */
	private boolean changeGroup(Classroom current, Classroom update, CourseLesson courseLesson) {
		boolean enableChange = !current.getLessonId().equals(update.getLessonId());
		enableChange |= !current.getOrganizationId().equals(update.getOrganizationId());
		enableChange &= courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT
				.getCode().intValue();

		return enableChange;
	}

	/**
	 * 校验数据课堂表数据是否可以改变
	 *
	 * @param classroom
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

	/**
	 * 数据拆分，数据不能跨天，跨几天则为几条
	 *
	 * @param classroomBo 课堂 bo
	 * @return {@code List<ClassroomBo> }
	 */
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
	 *
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
	 * 查询列表
	 *
	 * @param roomListQuery 房间列表查询
	 * @param authContext   auth 上下文
	 * @return {@code List<ClassroomBo> }
	 */
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

	/**
	 * 查询标志列表
	 *
	 * @param page        分页
	 * @param classroomId 课堂id
	 * @return {@code ListRsp<ClassroomMonitorBo> }
	 */
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

	/**
	 * 课堂统计
	 *
	 * @param classroomId 课堂id
	 * @param authContext auth 上下文
	 * @return {@code ClassroomStatisticBo }
	 */
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
		return ClassroomStatisticBo.builder().signInfo(signInfoBo)
				.top20(topNum).gradeDistribution(distributionBo).build();
	}

	/**
	 * 课堂习题数据处理
	 *
	 * @param paperEvaluate
	 * @param paper
	 * @param viewAnswer    视图答案
	 * @param authContext   auth 上下文
	 * @param lessonId      课id
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

	/**
	 * 实践详情
	 *
	 * @param classroomId 课堂id
	 * @param lessonId    课id
	 * @param userId      用户id
	 * @return {@code ClassroomPracticeDetailBo }
	 */
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

	/**
	 * 练习统计
	 *
	 * @param cpContext CP 上下文
	 * @param page      分页
	 * @return {@code ClassroomPracticeBo }
	 */
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

	/**
	 * 删除 Classroom
	 *
	 * @param classroomId 课堂id
	 * @param authContext auth 上下文
	 * @return {@code Integer }
	 */
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

	/**
	 * 获取聊天用户
	 *
	 * @param roomId      房间id
	 * @param userContext 用户上下文
	 * @return {@code DashboardInfo }
	 */
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

	/**
	 * 获取聊天群用户
	 *
	 * @param roomId  房间id
	 * @param groupId 群id
	 * @return {@code List<DashboardUser> }
	 */
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

	/**
	 * 删除教室
	 *
	 * @param orgIds 组织id列表
	 */
	@Override
	public void removeClassrooms(List<String> orgIds) {
		if (!orgIds.isEmpty()) {
			classroomMapper.deleteByOrgIds(orgIds);
		}
	}

	/**
	 * 查询分页
	 *
	 * @param roomListQuery 房间列表查询
	 * @param authContext   auth 上下文
	 * @return {@code IPage<ClassroomBo> }
	 */
	@Override
	public IPage<ClassroomBo> selectPage(ClassRoomPageQuery roomListQuery,
										 AuthContext authContext) {
		Page page = roomListQuery.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(authContext);
		IPage<Classroom> result = classroomMapper.page(page, roomListQuery.getUserId(),
				roomListQuery.getTrainPlanId(), roomListQuery.getChildrenOrg(), authContext.getIsAdmin(), courseIds);

		return result.convert(classroom -> entityToBo(classroom));

	}

	/**
	 * 查询id列表由计划id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> selectIdsByPlanIds(List<String> ids) {
		return classroomMapper.selectIdsByPlanIds(ids);
	}

	/**
	 * 进口训练计划
	 *
	 * @param realPath 实路
	 * @param userInfo 用户信息
	 * @param cover    盖
	 * @return {@code List<Classroom> }
	 */
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

	/**
	 * 查询临时任务id列表由用户
	 *
	 * @param userContext 用户上下文
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> selectTempTaskIdsByUser(UserInfo userContext) {

		return classroomMapper.selectTempTaskIdsByUser(userContext.getUserId(),userContext.isSuperuser()) ;
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

	/**
	 * 验证课堂时间
	 *
	 * @param classrooms 教室
	 */
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
	 *
	 * @param classrooms 教室
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

	/**
	 * 验证组织课相同
	 *
	 * @param classrooms 教室
	 */
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

	/**
	 * 校验数据长度
	 *
	 * @param sheetData 工作表数据
	 */
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

	/**
	 * 校验训练计划下课堂表是否开始 开始则提醒
	 *
	 * @param planId   计划id
	 * @param planName 计划名称
	 * @param cover    盖
	 * @param rowIndex 行索引
	 */
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

	/**
	 * 出口训练计划
	 *
	 * @param userInfo 用户信息
	 * @param filePath 文件路径
	 */
	@Override
	public void exportTrainPlan(UserInfo userInfo, String filePath) {
		// 数据查询及转换
		String userId =userInfo.isSuperuser() ? CommonConstant.EMPTY_STRING : userInfo.getUserId();

		List<TrainPlan> trainPlanList = trainPlanMapper.selectByUser(userId);

		List<TrainPlanExportBo> exportBoList = StreamApiUtils.flatMap(trainPlanList,
				trainPlan -> planToExportBo(userId, trainPlan));

		// 数据导出
		ExcelDataHandler.exportExcel(exportBoList, filePath, ExcelHeader.TRAIN_PLAN);
	}

	/**
	 * 导出的数据转换
	 * 会按照页面逻辑查询计划下的课程任务
	 *
	 * @param userId    用户id
	 * @param trainPlan 训练计划
	 * @return {@code List<TrainPlanExportBo> }
	 */
	private List<TrainPlanExportBo> planToExportBo(String userId, TrainPlan trainPlan) {
		List<Classroom> classroomList = classroomMapper.selectByUserPlan(userId,trainPlan.getResourceId());
		return StreamApiUtils.map(classroomList,
				classroom -> classRoomToExportBo(trainPlan.getName(), classroom));
	}

	/**
	 * 数据转换
	 *
	 * @param planName  计划名称
	 * @param classroom 课堂
	 * @return {@code TrainPlanExportBo }
	 */
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
			log.error("classroom binds lesson:[{}] doesn't exist", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		planExportBo.setCourseName(course.getName());
		planExportBo.setLessonName(lesson.getName());

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
	 *
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
	 *
	 * @param rowData
	 * @param rowIndex 行索引
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
	 *
	 * @param roomData
	 * @param userInfo
	 * @param rowIndex 行索引
	 * @param orgMap   组织结构图
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
	 *
	 * @param planId
	 * @param orgId
	 * @param startTime
	 * @param endTime
	 * @param rowIndex  行索引
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
	 *
	 * @param rowData
	 * @param userId
	 * @param cover
	 * @param rowIndex     行索引
	 * @param trainPlanMap 训练计划地图
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
	 *
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
	 * @param notes     笔记
	 * @param num       数量
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
	 * @param orgUserIds 组织用户id列表
	 * @param evaluates  评估
	 * @param signIns    登录
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
	 * 用户信息转换为监控信息
	 *
	 * @param userInfo
	 * @param userSignMap 用户标志图
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

}
