package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;

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

		room.setTrainingStatus(timeManagement.getStatus());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbRoomMapper.updateById(room);
				save(timeManagement);

			}
		});
	}

	@Override
	public String getTimeCost(String roomId) {
		return TimeUtil.duration2String(getTimeCostLong(roomId));
	}

	@Override
	public Long getTimeCostLong(String roomId) {
		RbTimeManagement timeManagement = getByRoomId(roomId);
		if (timeManagement == null) {
			return 0L;
		}
		// 暂停或者停止状态，返回已记录的训练时长
		if (timeManagement.isPaused() || timeManagement.isStopped()) {
			return timeManagement.getLength();
		} else {
			// 运行中（开始或者恢复后），返回已记录的训练时长 + 当前时间到恢复时间之间的差值
			return TimeUtil.getIntervalSeconds(timeManagement.getRecoverTime(), new Date())
					+ timeManagement.getLength();
		}
	}

	@Override
	public String getColonTimeCost(String roomId) {
		RbTimeManagement timeManagement = getByRoomId(roomId);
		if (timeManagement == null) {
			return TimeUtil.duration2String(0L);
		}
		if (timeManagement.isStopped()) {
			return TimeUtil.getColonTimeInterval(timeManagement.getCreateTime(),
					timeManagement.getUpdateTime());
		} else {
			return TimeUtil.getColonTimeInterval(timeManagement.getCreateTime(), new Date());
		}
	}

    @Override
    public Date getActualEndTime(String roomId) {
		RbTimeManagement management = this.getByRoomId(roomId);
		if(null != management && management.isStopped()){
			return management.getEndTime();
		} else {
			log.error("training not existing or not ended: [{}]", roomId);
			return null;
		}
    }
}
