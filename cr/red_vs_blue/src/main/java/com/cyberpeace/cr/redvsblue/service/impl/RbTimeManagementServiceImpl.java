package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbTimeManagementServiceImpl extends ServiceImpl<RbTimeManagementMapper, RbTimeManagement> implements RbTimeManagementService {

	@Autowired
	RbTimeManagementMapper rbTimeManagementMapper;

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
	public void updateTimeManagement(RbTimeManagement timeManagement, Integer status,
			String userId) {
		timeManagement.setStatus(status);
		timeManagement.setUpdateUserId(userId);

		// todo 逻辑待整理
		// if (timeManagement.isRecovering()){
		// Date currentCombatTime =
		// timeManagement.setCombatTime(timeManagement.get);
		// }
		updateById(timeManagement);
	}

}
