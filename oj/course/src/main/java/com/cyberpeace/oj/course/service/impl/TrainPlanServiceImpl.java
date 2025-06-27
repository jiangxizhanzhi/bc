package com.cyberpeace.oj.course.service.impl;

import java.util.*;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanWebReq;
import com.cyberpeace.oj.course.controller.res.*;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;
import com.cyberpeace.oj.ojbase.constant.TrainPlanStatusEnum;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.entity.Paper;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 训练计划Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see ServiceImpl
 * @see TrainPlanService
 * @since 1.0.0
 */
@Slf4j
@Service
public class TrainPlanServiceImpl extends ServiceImpl<TrainPlanMapper, TrainPlan> implements TrainPlanService {

	/**
	 * 训练计划Mapper
	 *
	 * @see TrainPlanMapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

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

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 训练计划列表
	 *
	 * @param userInfo 用户信息
	 * @return {@code List<TrainPlanBo> }
	 * @throws
	 * @see UserInfo
	 * @see List<TrainPlanBo>
	 */
	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	/**
	 * 分页
	 *
	 * @param trainPlanReq 训练计划请求体
	 * @param userInfo     用户信息
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see TrainPlanReq
	 * @see UserInfo
	 * @see IPage<TrainPlanRsp>
	 */
	@Override
	public IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo) {

		Page<TrainPlanRsp> page = trainPlanReq.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(AuthContextUtils.constructAuth(userInfo,
				false));
		IPage<TrainPlanRsp> result = trainPlanMapper.listTrainPlan(page, trainPlanReq.getPattern(), trainPlanReq.getOrganizationId(),
				userInfo.getUserId(), userInfo.isSuperuser(), userInfo.isAdmin(), courseIds,
				trainPlanReq.getStatus(), TrainPlan.defaultTaskSetName());

		// set status
		if (trainPlanReq.getStatus() != null) {
			result.getRecords().forEach(r -> r.setStatus(trainPlanReq.getStatus()));
		} else {
			for (TrainPlanRsp record : result.getRecords()) {
				Date now = new Date();
				if (record.getStartTime() == null) {
					record.setStatus(null);
				} else if (now.before(record.getStartTime())) {
					record.setStatus(TrainPlanStatusEnum.NOT_START.getCode());
				} else if (now.after(record.getEndTime())) {
					record.setStatus(TrainPlanStatusEnum.FINISHED.getCode());
				} else {
					record.setStatus(TrainPlanStatusEnum.IN_PROGRESS.getCode());
				}
			}
		}

		for (TrainPlanRsp trainPlanRsp : result.getRecords()) {
			UserInfo user = remoteUserService.getById(trainPlanRsp.getCreateUserId());
			if (null != user) {
				trainPlanRsp.setCreateUser( user.getName() );
			}
		}
		return result;
	}


	/**
	 * 我的教学课程-分页，包含子集分页
	 *
	 * @param trainPlanReq 训练计划请求体
	 * @param userInfo     用户信息
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see TrainPlanReq
	 * @see UserInfo
	 * @see IPage<TrainPlanRsp>
	 */
	@Override
	public IPage<TrainPlanWebRsp> page(TrainPlanWebReq trainPlanReq, UserInfo userInfo, List<String> courseIds) {

		Page<TrainPlanWebRsp> page = trainPlanReq.toPageParam();
		// 查询已授权的课程，计划状态和当前时间进行比较，未开始、进行中、已开始的计划
		IPage<TrainPlanWebRsp> result = trainPlanMapper.listTrainWebPlan(page, trainPlanReq.getPattern(), userInfo.getOrganizationId(),
				courseIds, trainPlanReq.getPlanStatus(), TrainPlan.defaultTaskSetName());

		for (TrainPlanWebRsp trainPlanRsp : result.getRecords()) {
			// 给空的计划设置默认计划和名称
			if (StringUtils.isEmpty(trainPlanRsp.getName())) {
				trainPlanRsp.setName(TrainPlan.defaultTaskSetName());
				trainPlanRsp.setResourceId(TrainPlan.TASK_SET_ID);
			}
			final String roomIds = trainPlanRsp.getRoomIds();
			if (StringUtils.isBlank(roomIds)) {
				continue;
			}

			final List<String> roomIdsList = StreamApiUtils.map(Arrays.asList(roomIds.split(CommonConstant.CODE_COMMA)), String::trim);

			List<TrainPlanLessonRsp> lessons = classroomMapper.selectListByIds(roomIdsList);
			final List<String> lessonIds = StreamApiUtils.map(lessons, TrainPlanLessonRsp::getLessonId);

			// 设置任务数
			trainPlanRsp.setMissionCountTotal(roomIdsList.size());

			// 查询课后练习数据， 按 lessonId 映射
			final List<TrainPlanLessonExerciseStatusRsp> trainPlanLessonExerciseStatusRsps = classroomMapper
					.selectLessonExerciseStatus(lessonIds, userInfo.getUserId());
			// 处理练习未下发
			List<String> paperIds = StreamApiUtils.map(trainPlanLessonExerciseStatusRsps, TrainPlanLessonExerciseStatusRsp::getPaperId);
			paperIds = StreamApiUtils.filter(paperIds, StringUtils::isNotBlank);
			List<Paper> papers = paperIds.isEmpty()? Lists.newArrayList() : paperMapper.selectBatchIds(paperIds);
			Set<String> releaseSet = Sets.newHashSet();
			if (StringUtils.isNotBlank(userInfo.getOrganizationId())) {
				for (Paper paper : papers) {
					if (StringUtils.isNotBlank(paper.getReadAuthIds())
							&& paper.getReadAuthIds().contains(userInfo.getOrganizationId())) {
						releaseSet.add(paper.getResourceId());
					}
				}
			}
			for (TrainPlanLessonExerciseStatusRsp rsp : trainPlanLessonExerciseStatusRsps) {
				if (rsp.getLevelStatus() == 1 && !releaseSet.contains(rsp.getPaperId())) {
					rsp.setLevelStatus(null);
				}
			}
			
			final Map<String, TrainPlanLessonExerciseStatusRsp> lessonExerciseStatusRspMap = StreamApiUtils
					.toMap(trainPlanLessonExerciseStatusRsps,
							TrainPlanLessonExerciseStatusRsp::getLessonId,
							trainPlanLessonExerciseStatusRsp -> trainPlanLessonExerciseStatusRsp);

			// 获取实验报告状态，按 lessonId 映射
			final List<TrainPlanLessonReportStatusRsp> trainPlanLessonReportStatusRsps = classroomMapper.selectLessonReportStatus(lessonIds, userInfo.getUserId());
			final Map<String, TrainPlanLessonReportStatusRsp> stringTrainPlanLessonReportStatusRspMap =
					StreamApiUtils.toMap(trainPlanLessonReportStatusRsps, TrainPlanLessonReportStatusRsp::getLessonId,
					trainPlanLessonReportStatusRsp -> trainPlanLessonReportStatusRsp);


			int experimentReportCountTotal = 0;
			int experimentReportCount = 0;

			int coursePracticeCount = 0;
			int coursePracticeCountTotal = releaseSet.size();

			for (TrainPlanLessonRsp trainPlanLessonRsp : lessons) {
				final TrainPlanLessonExerciseStatusRsp trainPlanLessonExerciseStatusRsp = lessonExerciseStatusRspMap.get(trainPlanLessonRsp.getLessonId());
				if (trainPlanLessonExerciseStatusRsp != null) {
					trainPlanLessonRsp.setLevelStatus(trainPlanLessonExerciseStatusRsp.getLevelStatus());
					trainPlanLessonRsp.setLevel(trainPlanLessonExerciseStatusRsp.getLevel());
					// 优良级别的判断为完成
					if (LessonPaperEvaluate.LevelType.isPass(trainPlanLessonExerciseStatusRsp.getLevel())) {
						coursePracticeCount += 1;
					}
				}

				final TrainPlanLessonReportStatusRsp trainPlanLessonReportStatusRsp = stringTrainPlanLessonReportStatusRspMap.get(trainPlanLessonRsp.getLessonId());
				if (trainPlanLessonReportStatusRsp != null) {
					trainPlanLessonRsp.setReportPassedStatus(trainPlanLessonReportStatusRsp.getReportPassedStatus());
					trainPlanLessonRsp.setReportScore(trainPlanLessonReportStatusRsp.getReportScore());
					trainPlanLessonRsp.setReportStatus(trainPlanLessonReportStatusRsp.getReportStatus());
					if (CourseLesson.LessonType.EXPERIMENT.getCode().equals(trainPlanLessonRsp.getLessonType())){
						if (Boolean.TRUE.equals(trainPlanLessonReportStatusRsp.getReportPassedStatus())) {
							experimentReportCount += 1;
						}
						experimentReportCountTotal += 1;
					}
				}
			}

			trainPlanRsp.setExperimentReportCount(experimentReportCount);
			trainPlanRsp.setExperimentReportCountTotal(experimentReportCountTotal);

			trainPlanRsp.setCoursePracticeCount(coursePracticeCount);
			trainPlanRsp.setCoursePracticeCountTotal(coursePracticeCountTotal);

			lessons.sort(Comparator.comparing(TrainPlanLessonRsp::getStartTime));
			trainPlanRsp.setLessons(lessons);
		}
		return result;
	}

	/**
	 * 检查名称重复
	 *
	 * @param name   名称
	 * @param id     id
	 * @param userId 用户id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	@Override
	public void checkNameRepeat(String name, String id, String userId) {
		LambdaQueryWrapper<TrainPlan> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TrainPlan::getName, name);
		queryWrapper.eq(TrainPlan::getCreateUserId, userId);

		if (StringUtils.isNotBlank(id)) {
			queryWrapper.ne(TrainPlan::getResourceId, id);
		}
		List<TrainPlan> assessModel = trainPlanMapper.selectList(queryWrapper);
		if (null != assessModel && assessModel.size()>0) {
			log.error("[{}] name repeat", StreamApiUtils.getColumn(assessModel, TrainPlan::getName).toString());
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	@Override
	public void reuse(TrainPlanUpdateReq reuseReq) {
		// 校验name是否已存在
		this.checkNameRepeat(reuseReq.getName(), null,
				UserInfoContext.getUserContext().getUserId());
		TrainPlan trainPlan = new TrainPlan();
		trainPlan.setName(reuseReq.getName());
		trainPlan.setResourceId(IDGeneratorUtil.getID());
		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		// classrooms
		List<Classroom> classroomList = classroomMapper.selectByPlan(reuseReq.getResourceId());
		String userId = UserInfoContext.getUserContext().getUserId();
		for (Classroom classroom : classroomList) {
			classroom.setCreateUserId(userId);
			classroom.setId(null);
			classroom.setCreateTime(null);
			classroom.setUpdateTime(null);
			classroom.setStartTime(null);
			classroom.setEndTime(null);
			classroom.setResourceId(IDGeneratorUtil.getID());
			classroom.setTrainPlanId(trainPlan.getResourceId());
			// 组织为空
			classroom.setOrganizationId(CommonConstant.EMPTY_STRING);
			classroom.setOrganizationIds(CommonConstant.EMPTY_LIST_JSON);
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				save(trainPlan);
				classroomService.saveBatch(classroomList);
			}
		});
	}

	/**
	 * 数据转换
	 *
	 * @param trainPlan 训练计划
	 * @param userInfo  用户信息
	 * @return {@code TrainPlanBo }
	 * @throws
	 * @see TrainPlan
	 * @see UserInfo
	 * @see TrainPlanBo
	 */
	private TrainPlanBo entityToBo(TrainPlan trainPlan, UserInfo userInfo) {
		TrainPlanBo planBo = BeanUtils.copy(trainPlan, TrainPlanBo.class);
		planBo.setTrainPlanName(trainPlan.getName());

		UserInfo user = remoteUserService.getById(trainPlan.getCreateUserId());
		if (null != user) {
			planBo.setUsername(userInfo.isSuperuser() ? user.getName() : CommonConstant.EMPTY_STRING);
		}
		return planBo;
	}
}
