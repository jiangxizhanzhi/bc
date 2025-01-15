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
import com.cyberpeace.cr.crbase.service.context.training.TrainingTimeCost;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;

import lombok.extern.slf4j.Slf4j;

/**
 * 红蓝对抗时间管理Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
@Slf4j
public class RbTimeManagementServiceImpl extends ServiceImpl<RbTimeManagementMapper, RbTimeManagement> implements RbTimeManagementService {

	/**
	 * 红蓝对抗时间管理Mapper
	 */
	@Autowired
	RbTimeManagementMapper rbTimeManagementMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * 红蓝对抗房间Mapper
	 */
	@Autowired
	RbRoomMapper rbRoomMapper;

	/**
	 * 获取由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code RbTimeManagement }
	 */
	@Override
	public RbTimeManagement getByRoomId(String roomId) {

		return rbTimeManagementMapper.selectByRoomId(roomId);
	}

	/**
	 * 获取开始房间id列表
	 *
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getStartedRoomIds() {
		return rbTimeManagementMapper
				.selectRoomIdsByStatus(RbTimeManagement.TimeManagementStatus.START.getCode());
	}

	/**
	 * 获取全部
	 *
	 * @return {@code List<RbTimeManagement> }
	 */
	@Override
	public List<RbTimeManagement> getAll() {
		return rbTimeManagementMapper.selectAll();
	}

	/**
	 * 获取停止
	 *
	 * @return {@code List<RbTimeManagement> }
	 */
	@Override
	public List<RbTimeManagement> getStopped() {
		return rbTimeManagementMapper
				.selectByStatus(RbTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	/**
	 * 获取未停止
	 *
	 * @return {@code List<RbTimeManagement> }
	 */
	@Override
	public List<RbTimeManagement> getUnstopped() {
		return rbTimeManagementMapper
				.selectExcludeStatus(RbTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	/**
	 * 更新时间管理
	 *
	 * @param timeManagement 时间管理
	 * @param room           房间
	 * @param status         状态
	 * @param userId         用户id
	 */
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

	/**
	 * 新增时间管理
	 *
	 * @param timeManagement 时间管理
	 * @param room           房间
	 * @param status         状态
	 * @param userId         用户id
	 */
	@Override
	public void addTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId) {
		timeManagement.setStatus(status);
		timeManagement.setCreateUserId(userId);
		timeManagement.setRecoverTime(new Date());
		timeManagement.setLength(0L);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbRoomMapper.updateTrainingStatus(room.getResourceId(), status);
				save(timeManagement);

			}
		});
	}

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
	@Override
	public String getTimeCost(String roomId) {
		return TimeUtil.duration2String(getTimeCostLong(roomId).getTimeCost());
	}

	/**
	 * 获取时间成本长
	 *
	 * @param roomId 房间id
	 * @return {@code TrainingTimeCost }
	 */
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

	/**
	 * 获取结肠时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
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

	/**
	 * 获取实体结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date }
	 */
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
