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
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
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

}
