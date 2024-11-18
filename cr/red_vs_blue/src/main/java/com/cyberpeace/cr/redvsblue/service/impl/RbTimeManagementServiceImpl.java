package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTimeManagementMapper;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;

import lombok.extern.slf4j.Slf4j;

/**
 * RB时间管理Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see RbTimeManagementService
 * @since 1.0.0
 */
@Service
@Slf4j
public class RbTimeManagementServiceImpl extends ServiceImpl<RbTimeManagementMapper, RbTimeManagement> implements RbTimeManagementService {

	/**
	 * RB时间管理Mapper
	 *
	 * @see RbTimeManagementMapper
	 */
	@Autowired
	RbTimeManagementMapper rbTimeManagementMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * RB房间Mapper
	 *
	 * @see RbRoomMapper
	 */
	@Autowired
	RbRoomMapper rbRoomMapper;

	/**
	 * 获取由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code RbTimeManagement }
	 * @throws
	 * @see String
	 * @see RbTimeManagement
	 */
	@Override
	public RbTimeManagement getByRoomId(String roomId) {

		return rbTimeManagementMapper.selectByRoomId(roomId);
	}

	/**
	 * 获取开始房间id列表
	 *
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 */
	@Override
	public List<String> getStartedRoomIds() {
		return rbTimeManagementMapper
				.selectRoomIdsByStatus(RbTimeManagement.TimeManagementStatus.START.getCode());
	}

	/**
	 * 获取都
	 *
	 * @return {@code List<RbTimeManagement> }
	 * @throws
	 * @see List<RbTimeManagement>
	 */
	@Override
	public List<RbTimeManagement> getAll() {
		return rbTimeManagementMapper.selectAll();
	}

	/**
	 * 获取停止
	 *
	 * @return {@code List<RbTimeManagement> }
	 * @throws
	 * @see List<RbTimeManagement>
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
	 * @throws
	 * @see List<RbTimeManagement>
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
	 * @throws
	 * @see RbTimeManagement
	 * @see RbRoom
	 * @see Integer
	 * @see String
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
	 * @throws
	 * @see RbTimeManagement
	 * @see RbRoom
	 * @see Integer
	 * @see String
	 */
	@Override
	public void addTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId) {
		timeManagement.setStatus(status);
		timeManagement.setCreateUserId(userId);

		room.setTrainingStatus(timeManagement.getStatus());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbRoomMapper.updateById(room);
				save(timeManagement);

			}
		});
	}

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getTimeCost(String roomId) {
		RbTimeManagement timeManagement = getByRoomId(roomId);
		if (timeManagement == null) {
			return TimeUtil.duration2String(0L);
		}
		if (timeManagement.isStopped()) {
			return TimeUtil.getTimeInterval(timeManagement.getCreateTime(),
					timeManagement.getUpdateTime());
		} else {
			return TimeUtil.getTimeInterval(timeManagement.getCreateTime(), new Date());
		}
	}

	/**
	 * 获取耗时
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
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

	/**
	 * 获取实体结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date }
	 * @throws
	 * @see String
	 * @see Date
	 */
	@Override
    public Date getActualEndTime(String roomId) {
		RbTimeManagement management = this.getByRoomId(roomId);
		if(null != management && management.isStopped()){
			return management.getUpdateTime();
		} else {
			log.error("training not existing or not ended: [{}]", roomId);
			return null;
		}
    }
}
