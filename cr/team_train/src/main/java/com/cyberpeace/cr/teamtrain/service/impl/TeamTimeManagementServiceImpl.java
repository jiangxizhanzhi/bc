package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTimeManagementMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTimeManagementService;
import com.cyberpeace.cs.service.LocalSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TeamTimeManagementServiceImpl extends ServiceImpl<com.cyberpeace.cr.teamtrain.dao.mapper.TeamTimeManagementMapper, TeamTimeManagement> implements TeamTimeManagementService {

	@Autowired
	TeamTimeManagementMapper teamTimeManagementMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private LocalSceneService localSceneService;

	@Override
	public TeamTimeManagement getByRoomId(String roomId) {

		return teamTimeManagementMapper.selectByRoomId(roomId);
	}

	@Override
	public List<String> getStartedRoomIds() {
		return teamTimeManagementMapper
				.selectRoomIdsByStatus(TeamTimeManagement.TimeManagementStatus.START.getCode());
	}

	@Override
	public List<TeamTimeManagement> getAll() {
		return teamTimeManagementMapper.selectAll();
	}

	@Override
	public List<TeamTimeManagement> getStopped() {
		return teamTimeManagementMapper
				.selectByStatus(TeamTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	@Override
	public List<TeamTimeManagement> getUnstopped() {
		return teamTimeManagementMapper
				.selectExcludeStatus(TeamTimeManagement.TimeManagementStatus.STOP.getCode());
	}

	@Override
	public void updateTimeManagement(TeamTimeManagement timeManagement, TeamRoom room, Integer status,
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
				if (TeamTimeManagement.TimeManagementStatus.STOP.getCode().equals(status)){
					String localSceneId = room.getLocalSceneId();
					room.setLocalSceneId("");
					// 清除业务数据、localscene表中数据
					localSceneService.removeById(localSceneId);
				}
				updateById(timeManagement);
				teamRoomMapper.updateById(room);
			}
		});
	}



	@Override
	public void addTimeManagement(TeamTimeManagement timeManagement, TeamRoom room, Integer status,
								  String userId) {
		timeManagement.setStatus(status);
		timeManagement.setCreateUserId(userId);

		room.setTrainingStatus(timeManagement.getStatus());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				teamRoomMapper.updateById(room);
				save(timeManagement);

			}
		});
	}

}
