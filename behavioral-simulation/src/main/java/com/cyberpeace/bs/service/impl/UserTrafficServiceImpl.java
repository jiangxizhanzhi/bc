package com.cyberpeace.bs.service.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.bs.dao.entity.UserBehavior;
import com.cyberpeace.bs.dao.entity.UserTraffic;
import com.cyberpeace.bs.dao.mapper.UserBehaviorMapper;
import com.cyberpeace.bs.dao.mapper.UserTrafficMapper;
import com.cyberpeace.bs.service.BsUtil;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserTrafficService;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.TrafficTimerTask;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.traffic.UserTrafficBo;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserTrafficServiceImpl implements UserTrafficService {

	private final Map<String, Timer> timerMap = Maps.newConcurrentMap();

	@Autowired
	UserTrafficMapper userTrafficMapper;

	@Autowired
	UserBehaviorMapper userBehaviorMapper;

	@Autowired
	RemoteSceneService remoteSceneService;

	@Autowired
	LcService lcService;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public List<UserTrafficBo> listTraffic() {
		List<UserTraffic> traffics = userTrafficMapper.selectAll();
		List<UserTrafficBo> trafficBos = BeanUtils.deepCopyList(traffics, UserTrafficBo.class);

		return trafficBos;
	}

	@Override
	public void batchDelete(List<String> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		userTrafficMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public void create(String userBehaviorId) {
		if (StringUtils.isBlank(userBehaviorId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserBehavior userBehavior = userBehaviorMapper.selectById(userBehaviorId);
		if (userBehavior == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		UserTraffic traffic = createUserTraffic(userBehavior);
		userTrafficMapper.insert(traffic);
	}

	@Override
	public void startTraffic(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserTraffic traffic = userTrafficMapper.selectById(resourceId);
		if (traffic == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// todo update userTraffic
		String ip = remoteSceneService.startTarget(traffic.getAccessOs());
		traffic.setTargetIp(ip);
		traffic.setStatus(UserTraffic.Status.INITIATING.getCode());
		userTrafficMapper.updateById(traffic);

		// create timer
		Timer timer = new Timer();
		List<BehaviorSimple> behaviorList = BsUtil.parseBehaviorList(traffic.getBehaviorList());
		LocalTime now = LocalTime.now();
		int index = behaviorList.size();
		for (BehaviorSimple behavior : behaviorList) {
			// 最后一个task index为0
			index--;
			if (behavior.getStartTime().isBefore(now)) {
				continue;
			}
			TrafficTimerTask task = new TrafficTimerTask(ip, behavior.getId(), index, resourceId);
			timer.schedule(task, BsUtil.getDateToday(behavior.getStartTime()));
		}
		timerMap.putIfAbsent(resourceId, timer);
	}

	@Override
	public void stopTraffic(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserTraffic traffic = userTrafficMapper.selectById(resourceId);
		if (traffic == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// cancel timer
		Timer timer = timerMap.get(resourceId);
		timer.cancel();
		timerMap.remove(resourceId);
		// todo stop running behavior
		// get latest behavior id
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(traffic.getBehaviorList());
		if (!traffic.getExecutedIndex().equals(behaviorSimpleList.size())) {
			// stop latest executed behavior
			String latestBehaviorId = behaviorSimpleList
					.get(behaviorSimpleList.size() - traffic.getExecutedIndex() - 1).getId();
			lcService.stopTraffic(traffic.getTargetIp(), latestBehaviorId);
		}
		// update status
		traffic.setStatus(UserTraffic.Status.STOPPED.getCode());
	}

	@Override
	public Map<String, Timer> getTimerMap() {
		return timerMap;
	}

	@Override
	public void updateProcess(String trafficId, Integer index) {
		UserTraffic traffic = userTrafficMapper.selectById(trafficId);
		traffic.setExecutedIndex(index);
		if (index == 0) {
			traffic.setStatus(UserTraffic.Status.STOPPED.getCode());
		}
		userTrafficMapper.updateById(traffic);
	}

	private UserTraffic createUserTraffic(UserBehavior userBehavior) {
		UserTraffic traffic = new UserTraffic();
		BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userBehavior.getBehaviorList());
		traffic.setUserBehaviorId(userBehavior.getResourceId());
		traffic.setUserName(userBehavior.getUserName());
		traffic.setUserType(userBehavior.getUserType());
		traffic.setStartTime(statistic.getStartTime());
		traffic.setEndTime(statistic.getEndTime());
		traffic.setAccessConfig(userBehavior.getAccessConfig());
		traffic.setAccessOs(userBehavior.getAccessOs());
		traffic.setBehaviorCount(statistic.getBehaviorCount());
		traffic.setStatus(UserTraffic.Status.NOT_STARTED.getCode());
		traffic.setExecutedIndex(statistic.getBehaviorCount());

		traffic.setBehaviorList(userBehavior.getBehaviorList());

		return traffic;
	}
}
