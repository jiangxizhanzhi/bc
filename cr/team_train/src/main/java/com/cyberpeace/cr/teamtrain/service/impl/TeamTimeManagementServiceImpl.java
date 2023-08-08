package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTimeManagementMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTimeManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class TeamTimeManagementServiceImpl extends ServiceImpl<com.cyberpeace.cr.teamtrain.dao.mapper.TeamTimeManagementMapper, TeamTimeManagement> implements TeamTimeManagementService {

	@Autowired
	TeamTimeManagementMapper teamTimeManagementMapper;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public TeamTimeManagement getByRoomId(String roomId) {
		return teamTimeManagementMapper.selectByRoomId(roomId);
	}

}
