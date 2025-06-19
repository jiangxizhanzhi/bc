package com.cyberpeace.ai.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.ai.model.enums.UserProfileDimensionEnum;
import com.cyberpeace.ai.model.mapper.UserProfileMapper;
import com.cyberpeace.ai.model.vo.*;
import com.cyberpeace.ai.model.vo.course.CourseListDataVO;
import com.cyberpeace.ai.model.vo.practice.PracticeDataVO;
import com.cyberpeace.ai.model.vo.practice.PracticeTheoryDataVO;
import com.cyberpeace.ai.model.vo.train.TrainListDataVO;
import com.cyberpeace.ai.model.vo.train.TrainStatisticsDataVO;
import com.cyberpeace.ai.service.UserProfileService;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.res.RoleDetailRes;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.skill.SkillProcessRes;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private CrBaseService crBaseService;

	@Autowired
	private TrainRecordMapper trainRecordMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private TrainingPlanService trainingPlanService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private UtilService utilService;

	@Override
	public UserProfileReportVO getUserProfileData(String dimension) {
		UserProfileReportVO vo = new UserProfileReportVO();
		vo.setUserName(UserInfoContext.getUserContext().getUserName());
		vo.setDimension(dimension);
		if (dimension.contains(UserProfileDimensionEnum.COURSE_LEARNING.getValueStr())) {
			UserProfileCourseDataVO courseDataVO = this.getCourseData();
			vo.setCourseData(courseDataVO);
		}
		if (dimension.contains(UserProfileDimensionEnum.PRACTICE.getValueStr())) {
			UserProfilePracticeDataVO practiceData = this.getPracticeData();
			vo.setPracticeData(practiceData);
		}
		if (dimension.contains(UserProfileDimensionEnum.TRAINING.getValueStr())) {
			UserProfileTrainDataVO trainData = this.getTrainData();
			vo.setTrainingData(trainData);
		}
		if (dimension.contains(UserProfileDimensionEnum.EVALUATION.getValueStr())) {
			UserProfileEvaluationDataVO evaluationData = this.getEvaluationData();
			vo.setEvaluationData(evaluationData);
		}

		return vo;
	}

	private UserProfileTrainDataVO getTrainData() {
		UserProfileTrainDataVO dataVO = new UserProfileTrainDataVO();
		// overall
		PageReqBase req = new PageReqBase();
		req.setPerPage(99999);
		TrainingStatisticsRsp rsp = crBaseService
				.getTrainingStatistics(UserInfoContext.getUserContext().getUserId(), req);
		TrainStatisticsDataVO overall = new TrainStatisticsDataVO();
		overall.setCompleted(rsp.getCompleted());
		overall.setIncomplete(rsp.getIncomplete());
		overall.setTrainingTypeMap(rsp.getTrainingTypeMap());
		dataVO.setOverallStats(overall);
		// task list
		dataVO.setTaskCompletion(this.getTrainHisList(rsp.getRelation().getList()));
		return dataVO;
	}

	private List<TrainListDataVO> getTrainHisList(List<TrainingData> trainingDataList) {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<TrainListDataVO> res = Lists.newArrayList();
		for (TrainingData data : trainingDataList) {
			// 单兵单独统计
			if (data.getAppId().equals(BusinessCode.SINGLE_TRAINING.getCode())) {
				continue;
			}
			TrainListDataVO vo = new TrainListDataVO();
			vo.setResourceId(data.getResourceId());
			vo.setName(data.getName());
			vo.setType(data.getAppName());
			vo.setStartTime(data.getStartTime());
			res.add(vo);
		}
		// single record
		LambdaQueryWrapper<TrainRecord> recordWrapper = new LambdaQueryWrapper<>();
		recordWrapper.eq(TrainRecord::getUserId, userId);
		recordWrapper.eq(TrainRecord::getBusinessType, BusinessType.TRAINING_SINGLE.getCode());
		recordWrapper.isNotNull(TrainRecord::getExtra);
		List<TrainRecord> singleRecords = trainRecordMapper.selectList(recordWrapper);
		for (TrainRecord singleRecord : singleRecords) {
			TrainListDataVO vo = new TrainListDataVO();
			vo.setResourceId(singleRecord.getRoomId());
			vo.setName(singleRecord.getRoomName());
			vo.setType(BusinessCode.SINGLE_TRAINING.getDesc());
			vo.setStartTime(singleRecord.getStartTime());

			res.add(vo);
		}
		// tasks
		List<String> singleRoomIds = StreamApiUtils.map(singleRecords, TrainRecord::getRoomId);
		List<String> allRoomIds = StreamApiUtils.map(res, TrainListDataVO::getResourceId);
		allRoomIds.addAll(singleRoomIds);
		if (allRoomIds.isEmpty()) {
			return res;
		}
		List<CrTask> taskList = crTaskMapper.selectList(new LambdaQueryWrapper<CrTask>()
				.in(CrTask::getRoomId, allRoomIds).eq(CrTask::getUserId, userId));
		List<String> taskIds = StreamApiUtils.map(taskList, CrTask::getResourceId);

		List<CrTaskSubmit> submitList = crTaskSubmitMapper.selectList(
				new LambdaQueryWrapper<CrTaskSubmit>().in(CrTaskSubmit::getTaskId, taskIds));
		Map<String, List<CrTask>> roomTaskMap = StreamApiUtils.groupBy(taskList, CrTask::getRoomId);
		Map<String, List<CrTaskSubmit>> submitMap = StreamApiUtils.groupBy(submitList,
				CrTaskSubmit::getTaskId);
		// statistics
		for (TrainListDataVO vo : res) {
			List<CrTask> tasks = roomTaskMap.getOrDefault(vo.getResourceId(), Lists.newArrayList());
			vo.setTotal(tasks.size());
			int finish = 0;
			// 提交判分之后才算完成
			for (CrTask task : tasks) {
				List<CrTaskSubmit> submits = submitMap.getOrDefault(task.getResourceId(),
						Lists.newArrayList());
				for (CrTaskSubmit submit : submits) {
					if (submit.evaluated()) {
						finish++;
						break;
					}
				}
			}
			vo.setCompleted(finish);
		}
		res.sort(Comparator.comparing(TrainListDataVO::getStartTime).reversed());
		return res;
	}

	private UserProfileEvaluationDataVO getEvaluationData() {
		UserProfileEvaluationDataVO dataVO = new UserProfileEvaluationDataVO();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		// role & level
		Role role = roleService.getOr404(roleId);
		dataVO.setRoleName(role.getName());
		dataVO.setAvatar(utilService.handlerFileUrl(role.getAvatar()));
		String levelId = role.getLevelId();
		String fullPath = levelService.getFullPathById(levelId);
		dataVO.setLevel(fullPath);

		PageReqBase pageReqBase = new PageReqBase();
		pageReqBase.setPerPage(99999);
		// skill point
		List<SkillProcessRes> skillProcessRes = Lists.newArrayList();

		if (StringUtils.isNotBlank(roleId)) {
			skillProcessRes = skillService.pageSkillListByRoleId(pageReqBase, roleId).getList();
		}
		List<PointDataVO> skillPoints = StreamApiUtils.map(skillProcessRes, skill -> {
			PointDataVO vo = new PointDataVO();
			vo.setContent(skill.getContent());
			vo.setMastery(skill.getProcess());
			return vo;
		});
		dataVO.setSkillPoints(skillPoints);
		// knowledge point
		List<KnowledgeRes> knowledgeResList = Lists.newArrayList();
		if (StringUtils.isNotBlank(roleId)) {
			knowledgeResList = trainingPlanService.getKnowledgeCourseList(pageReqBase, roleId)
					.getList();

		}
		List<PointDataVO> knowledgePoints = StreamApiUtils.map(knowledgeResList, knowledge -> {
			PointDataVO vo = new PointDataVO();
			vo.setContent(knowledge.getContent());
			vo.setMastery(knowledge.getProcess());
			return vo;
		});
		dataVO.setKnowledgePoints(knowledgePoints);

		return dataVO;
	}

	private UserProfilePracticeDataVO getPracticeData() {
		UserProfilePracticeDataVO dataVo = new UserProfilePracticeDataVO();
		// 理论题-总体练习情况
		ProgressStatisticsRes theoryOverallStats = practiceTheoryService
				.getProgressStatistics(null);
		dataVo.setTheoryOverallStats(theoryOverallStats);
		// 理论题-各方向练习情况
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		List<String> practiceIds = practiceTheoryService.getPracticeIdsByUserAndSet(authContext,
				null);
		if (!practiceIds.isEmpty()) {
			List<PracticeTheoryDataVO> theoryDirectionStats = userProfileMapper
					.statisticTheoryByType(practiceIds, authContext.getUserId());
			theoryDirectionStats = StreamApiUtils.filter(theoryDirectionStats,
					t -> t.getCount() > 0);
			List<PracticeCategory> categoryList = practiceCategoryService.list();
			Map<String, String> categoryMap = StreamApiUtils.toMap(categoryList,
					PracticeCategory::getResourceId, PracticeCategory::getName);
			for (PracticeTheoryDataVO vo : theoryDirectionStats) {
				vo.setDirection(categoryMap.get(vo.getCategoryId()));
				vo.setErrorCount(vo.getPracticeCount() - vo.getSolvedCount());
			}
			dataVo.setTheoryDirectionStats(theoryDirectionStats);
		}
		// 实操题-总体练习情况
		List<CategoryProgressRes> categoryProgressResList = practiceOperationService
				.getOperationCategoryProgress();
		categoryProgressResList
				.sort(Comparator.comparing(categoryProgressRes -> categoryProgressRes.getCount()
						* 1.0 / categoryProgressRes.getSum()));
		Collections.reverse(categoryProgressResList);
		List<CategoryProgressRes> overall = categoryProgressResList;
		if (categoryProgressResList.size() > 6) {
			overall = categoryProgressResList.subList(0, 6);
		}
		dataVo.setPracticalOverallStats(overall);
		// 实操题-练习数量
		categoryProgressResList = StreamApiUtils.filter(categoryProgressResList,
				r -> r.getSum() > 0);
		for (CategoryProgressRes categoryProgressRes : categoryProgressResList) {
			PracticeDataVO vo = new PracticeDataVO();
			vo.setDirection(categoryProgressRes.getName());
			vo.setCount(categoryProgressRes.getSum());
			vo.setSolved(categoryProgressRes.getCount());
			dataVo.getPracticalDirectionStats().add(vo);
		}
		return dataVo;
	}

	private UserProfileCourseDataVO getCourseData() {
		UserProfileCourseDataVO res = new UserProfileCourseDataVO();
		UserInfo userInfo = UserInfoContext.getUserContext();
		// courses
		List<Course> allCourse = courseService.list();
		Map<String, Course> courseMap = StreamApiUtils.toMap(allCourse, Course::getResourceId,
				c -> c);
		List<CourseCategory> courseCategoryList = courseCategoryService.list();
		Map<String, String> categoryMap = StreamApiUtils.toMap(courseCategoryList,
				CourseCategory::getResourceId, CourseCategory::getName);
		List<CourseStudyProcessBo> processBoList = courseStatisticsService.statisticByIds(
				StreamApiUtils.map(allCourse, Course::getResourceId), userInfo.getUserId());
		// 课程完成度分析
		for (CourseStudyProcessBo bo : processBoList) {
			CourseListDataVO vo = new CourseListDataVO();
			vo.setName(bo.getCourseName());
			vo.setDuration(bo.getCompleteDuration());
			Course course = courseMap.get(bo.getCourseId());
			vo.setModuleName(null == course ? "" : categoryMap.get(course.getCategoryId()));
			if (bo.getProcess() == 100.0) {
				vo.setFinishDate(TimeUtil.format6(bo.getLastStudyTime()));
				vo.setFinished(true);
				// vo.setStatus("已完成");
			} else {
				vo.setFinished(false);
				// vo.setStatus("未完成");
			}
			res.getCourseList().add(vo);
		}
		// 饼图
		RoleStudyBo studyBo = rtksStatisticService.getStudyBo(processBoList);
		res.setStudyStatistics(studyBo);
		// 知识点掌握情况
		List<KnowledgeRes> knowledgeResList = knowledgeMapper.selectAll();
		knowledgeService.checkKnowledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_STUDY);
		knowledgeResList = StreamApiUtils.filter(knowledgeResList, k -> k.getProcess() > 0);
		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			PointDataVO vo = new PointDataVO();
			vo.setContent(knowledgeRes.getContent());
			vo.setMastery(knowledgeRes.getProcess());
			res.getKnowledgePoints().add(vo);
		}
		return res;
	}
}
