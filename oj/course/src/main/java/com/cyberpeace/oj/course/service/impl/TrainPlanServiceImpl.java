package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;
import com.cyberpeace.oj.course.service.bo.classroom.PlanProgressBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainPlanServiceImpl extends ServiceImpl<TrainPlanMapper, TrainPlan> implements TrainPlanService {

	@Autowired
	private TrainPlanMapper trainPlanMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;
	
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	@Override
	public IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo) {

		Page page = trainPlanReq.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(AuthContextUtils.constructAuth(userInfo,
				false));
		IPage<TrainPlanRsp> result = trainPlanMapper.listTrainPlan(page, trainPlanReq.getPattern(), trainPlanReq.getOrganizationId(),
				userInfo.getUserId(), userInfo.isSuperuser(), userInfo.isAdmin(), courseIds, trainPlanReq.getStatus());
		
		List<String> userIds = result.getRecords().stream().map(TrainPlanRsp::getCreateUserId).collect(Collectors.toList());
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
		for (TrainPlanRsp trainPlanRsp : result.getRecords()) {
			trainPlanRsp.setCreateUser(userNameMap.get(trainPlanRsp.getCreateUserId()));
		}
		// set status
		if (trainPlanReq.getStatus() != null) {
			result.getRecords().forEach(r -> r.setStatus(trainPlanReq.getStatus()));
		} else {
			for (TrainPlanRsp record : result.getRecords()) {
				Date now = new Date();
				if (record.getStartTime() == null) {
					record.setStatus(null);
				} else if (now.before(record.getStartTime())) {
					record.setStatus(TrainPlan.PlanStatus.NOT_STARTED);
				} else if (now.after(record.getEndTime())) {
					record.setStatus(TrainPlan.PlanStatus.ENDED);
				} else {
					record.setStatus(TrainPlan.PlanStatus.ONGOING);
				}
			}
		}
		// org
		Set<String> orgIdList = result.getRecords().stream().map(TrainPlanRsp::getOrganizationId)
				.collect(Collectors.toSet());
		Map<String, String> orgNameMap = Maps.newHashMap();
		Map<String, String> orgPathMap = Maps.newHashMap();
		Map<String, List<String>> parentIdsMap = Maps.newHashMap();
		for (String orgId : orgIdList) {
			orgNameMap.put(orgId, remoteOrganizationService.orgNameById(orgId));
			orgPathMap.put(orgId, remoteOrganizationService.getOrgPath(orgId));
			parentIdsMap.put(orgId, remoteOrganizationService.getParentIds(orgId));
		}
		for (TrainPlanRsp rsp : result.getRecords()) {
			rsp.setOrganizationName(orgNameMap.get(rsp.getOrganizationId()));
			rsp.setOrgPath(orgPathMap.get(rsp.getOrganizationId()));
			rsp.setParentOrgIds(parentIdsMap.get(rsp.getOrganizationId()));
		}
		return result;
	}

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
		TrainPlan trainPlan = BeanUtils.copy(reuseReq, TrainPlan.class);
		trainPlan.setResourceId(IDGeneratorUtil.getID());
		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		List<String> orgIds = classroomValidateHandler
				.validateClassroomOrgHasUser(trainPlan.getOrganizationId());
		trainPlan.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		// classrooms
		List<Classroom> classroomList = classroomMapper.selectByPlan(reuseReq.getResourceId());
		String userId = UserInfoContext.getUserContext().getUserId();
		for (Classroom classroom : classroomList) {
			classroom.setCreateUserId(userId);
			classroom.setId(null);
			classroom.setCreateTime(null);
			classroom.setUpdateTime(null);
			classroom.setResourceId(IDGeneratorUtil.getID());
			classroom.setTrainPlanId(trainPlan.getResourceId());
			classroom.setOrganizationId(trainPlan.getOrganizationId());
			classroom.setOrganizationIds(trainPlan.getOrganizationIds());
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				save(trainPlan);
				classroomService.saveBatch(classroomList);
			}
		});

	}

	@Override
	public TrainPlan getPlanById(String trainPlanId) {
		TrainPlan plan = this.getById(trainPlanId);
		if (null == plan) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_NOT_FOUND);
		}
		if (StringUtils.isBlank(plan.getOrganizationId())) {
			throw new BusinessException(OjBusinessErrorCode.PLAN_ORG_IS_NULL);
		}
		return plan;
	}

	@Override
	public List<PlanProgressBo> getProgress(String planId) {
		TrainPlan plan = this.getPlanById(planId);
		List<PlanProgressBo> res = Lists.newArrayList();
		List<UserInfo> userInfoList = remoteUserService.listByOrgId(plan.getOrganizationId());
		if (userInfoList.isEmpty()) {
			return res;
		}
		List<String> userIds = StreamApiUtils.map(userInfoList, UserInfo::getUserId);
		// exp only
		List<Classroom> classroomList = classroomMapper.listExpByPlanId(planId);
		List<String> lessonIds = StreamApiUtils.map(classroomList, Classroom::getLessonId);
		List<Note> noteList = noteService.listClassReport(userIds, lessonIds);
		int expCount = classroomList.size();
		Map<String, List<Note>> userNoteMap = StreamApiUtils.groupBy(noteList, Note::getUserId);

		Map<String, List<ClassroomSign>> signMap = Maps.newHashMap();
		if (!lessonIds.isEmpty()) {
			List<String> classroomIds = StreamApiUtils.map(classroomList, Classroom::getResourceId);
			List<ClassroomSign> signList = classroomSignMapper.selectByRoomsAndUsers(classroomIds,
					userIds);
			signMap = StreamApiUtils.groupBy(signList, ClassroomSign::getUserId);
		}

		for (UserInfo info : userInfoList) {
			PlanProgressBo bo = new PlanProgressBo();
			bo.setStudentId(info.getUserId());
			bo.setName(info.getName());
			bo.setOrgId(info.getOrganizationId());
			bo.setOrgName(info.getOrgName());
			bo.setUsername(info.getUserName());
			List<Note> reportList = userNoteMap.getOrDefault(info.getUserId(),
					Lists.newArrayList());
			int finishedExp = reportList.size();
			bo.setProgress(MathUtils.roundDivide(finishedExp * 100, expCount));
			// time cost
			List<ClassroomSign> signList = signMap.getOrDefault(info.getUserId(),
					Lists.newArrayList());
			Map<String, ClassroomSign> individualSignMap = StreamApiUtils.toMap(signList,
					ClassroomSign::getClassroomId, s -> s);
			Map<String, Note> individualReportMap = StreamApiUtils.toMap(reportList,
					Note::getBusinessId, n -> n);
			int cost = 0;
			for (Classroom classroom : classroomList) {
				ClassroomSign sign = individualSignMap.get(classroom.getResourceId());
				if (null == sign) {
					continue;
				}
				long classroomCost = 0;
				Date start = sign.getSignInTime();
				Note note = individualReportMap.get(classroom.getLessonId());
				Date end;
				if (null == note) {
					end = new Date();
				} else {
					// 最后一次提交的时间
					end = note.getUpdateTime();
				}
				if (end.before(start)) {
					classroomCost = 0;
				} else {
					classroomCost = TimeUtil.getIntervalSeconds(start, end);
				}

				cost += classroomCost;
			}
			bo.setTimeCost(cost);
			res.add(bo);
		}
		return res;
	}

    /** 数据转换*/
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
