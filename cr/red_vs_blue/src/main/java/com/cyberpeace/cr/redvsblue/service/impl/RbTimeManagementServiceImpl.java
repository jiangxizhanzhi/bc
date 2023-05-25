package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.service.context.training.TrainingTimeCost;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbTimeManagementServiceImpl extends ServiceImpl<RbTimeManagementMapper, RbTimeManagement> implements RbTimeManagementService {

	@Autowired
	RbTimeManagementMapper rbTimeManagementMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Override
	public RbTimeManagement getByRoomId(String roomId) {

		return rbTimeManagementMapper.selectByRoomId(roomId);
	}

	@Override
	public List<String> getStartedRoomIds() {
		return rbTimeManagementMapper
				.selectRoomIdsByStatus(RbTimeManagement.TimeManagementStatus.START.getCode());
	}

	@Override
	public List<RbTimeManagement> getAll() {
		return rbTimeManagementMapper.selectAll();
	}

	@Override
	public List<RbTimeManagement> getStopped() {
		return rbTimeManagementMapper
				.selectByStatus(RbTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	@Override
	public List<RbTimeManagement> getUnstopped() {
		return rbTimeManagementMapper
				.selectExcludeStatus(RbTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	@Override
	public void updateTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId) {
		timeManagement.setStatus(status);
		timeManagement.setUpdateUserId(userId);

		room.setTrainingStatus(status);

		// 暂停或者停止，更新天文时间
		if (timeManagement.isPaused() || timeManagement.isStopped()) {

			long time = timeManagement.getCombatTime().getTime()
					+ (System.currentTimeMillis() - timeManagement.getUpdateTime().getTime())
							* timeManagement.getRate();
			Date currentCombatTime = new Date(time);
			timeManagement.setCombatTime(currentCombatTime);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(timeManagement);
				rbRoomMapper.updateById(room);
				if (timeManagement.isStopped()) {
					trainRecordService.updateEndTimeByRoomId(timeManagement.getRoomId(),
							new Date());
				}
			}
		});
	}

	@Override
	public void addTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId) {
		timeManagement.setStatus(status);
		timeManagement.setCreateUserId(userId);
		timeManagement.setRecoverTime(new Date());
		timeManagement.setLength(0L);

		// get student ids
		List<String> studentIds = getStudentIds(room.getResourceId());

		// 获取团队训练业务模板id
		RbTemplate template = rbTemplateService.getById(room.getTemplateId());
		String businessTemplateId;
		// 模板可能已被删除。
		if (template == null) {
			businessTemplateId = "deleted";
		} else {
			businessTemplateId = template.getBusinessTemplateId();
		}

		room.setTrainingStatus(timeManagement.getStatus());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbRoomMapper.updateById(room);
				save(timeManagement);
				trainRecordService.saveRecord(room.getName(), businessTemplateId,
						room.getResourceId(), UserInfoContext.getUserContext().getUserId(),
						BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(), studentIds);

			}
		});
	}

	private List<String> getStudentIds(String roomId) {
		List<String> res = Lists.newArrayList();
		List<CrRole> roleList = crRoleService.getRolesByRoom(roomId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		for (CrRole role : roleList) {
			if (role.isRvbStudent()) {
				res.addAll(JsonUtil.toJsonObjectArray(role.getUserIds(), String.class));
			}
		}
		return res;
	}

	@Override
	public String getTimeCost(String roomId) {
		return TimeUtil.duration2String(getTimeCostLong(roomId).getTimeCost());
	}

	@Override
	public TrainingTimeCost getTimeCostLong(String roomId) {
		TrainingTimeCost timeCost = new TrainingTimeCost();
		RbTimeManagement timeManagement = getByRoomId(roomId);
		if (timeManagement == null) {
			return timeCost;
		}
		// 暂停或者停止状态，返回已记录的训练时长
		if (timeManagement.isPaused() || timeManagement.isStopped()) {
			timeCost.setRunning(false);
			// 5.4.1向前兼容
			if (timeManagement.getLength() == 0) {
				timeCost.setTimeCost(TimeUtil.getIntervalSeconds(timeManagement.getCreateTime(),
						timeManagement.getUpdateTime()));
			} else {
				timeCost.setTimeCost(timeManagement.getLength());
			}
		} else {
			// 运行中（开始或者恢复后），返回已记录的训练时长 + 当前时间到恢复时间之间的差值
			timeCost.setRunning(true);
			timeCost.setTimeCost(
					TimeUtil.getIntervalSeconds(timeManagement.getRecoverTime(), new Date())
							+ timeManagement.getLength());
		}
		return timeCost;
	}

	@Override
	public String getColonTimeCost(String roomId) {
		RbTimeManagement timeManagement = getByRoomId(roomId);
		if (timeManagement == null) {
			return TimeUtil.duration2String(0L);
		}
		if (Boolean.TRUE.equals(timeManagement.isStopped())) {
			return TimeUtil.getColonTimeInterval(timeManagement.getCreateTime(),
					timeManagement.getUpdateTime());
		} else {
			return TimeUtil.getColonTimeInterval(timeManagement.getCreateTime(), new Date());
		}
	}

	@Override
	public Date getActualEndTime(String roomId) {
		RbTimeManagement management = this.getByRoomId(roomId);
		if (null != management && management.isStopped()) {
			// 兼容升级到5.4.1之前就结束的训练
			return management.getEndTime() == null ? management.getUpdateTime()
					: management.getEndTime();
		} else {
			log.error("training not existing or not ended: [{}]", roomId);
			return null;
		}
	}
}
