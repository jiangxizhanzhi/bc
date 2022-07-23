package com.cyberpeace.oj.course.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.StartSceneBo;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchStartSceneHandler {

	private List<StartSceneBo> groupList;

	private Map<String, StartSceneBo> tasks = Maps.newHashMap();

	private Lock currentLock = new ReentrantLock();

	private Integer timeCount = Integer.parseInt("3");

	private ClassroomSceneService classroomSceneService;

	public BatchStartSceneHandler(UserInfo userInfo, List<ClassroomGroupInfo> groupInfos,
			ClassroomSceneService sceneService) {
		groupList = initStartBo(userInfo, groupInfos);
		this.classroomSceneService = sceneService;
	}

	/**
	 * 场景启动完成
	 * @param groupId
	 */
	public void finish(String groupId) {
		currentLock.lock();
		try {
			if (tasks.isEmpty()) {
				batchStart();
				return;
			}

			tasks.remove(groupId);
			distribution();
		} finally {
			currentLock.unlock();
		}
	}

	/**
	 * 重新分配数据
	 */
	private void distribution() {
		currentLock.lock();
		try {
			if (!tasks.isEmpty()) {
				return;
			}

			if (groupList.isEmpty()) {
				return;
			}

			int distribution = timeCount < groupList.size() ? timeCount : groupList.size();
			for (int i = 0; i < distribution; i++) {
				StartSceneBo sceneBo = groupList.remove(CommonConstant.ZERO.intValue());
				tasks.put(sceneBo.getGroupId(), sceneBo);
			}

			new Thread(new BatchStart()).start();
		} finally {
			currentLock.unlock();
		}
	}

	/**
	 * 执行批量启动
	 */
	public void batchStart() {
		distribution();
	}

	private List<StartSceneBo> initStartBo(UserInfo userInfo, List<ClassroomGroupInfo> groupInfos) {
		return StreamApiUtils.map(groupInfos, groupInfo -> {
			StartSceneBo startSceneBo = new StartSceneBo();
			startSceneBo.setAuthContext(AuthContextUtils.constructAuth(userInfo, false));
			startSceneBo.setGroupId(groupInfo.getResourceId());
			startSceneBo.setUserInfo(userInfo);
			return startSceneBo;
		});
	}

	public void addStartScene(UserInfo userInfo, List<ClassroomGroupInfo> groupInfos) {
		currentLock.lock();
		try {
			List<StartSceneBo> startSceneBos = initStartBo(userInfo, groupInfos);
			groupList.addAll(startSceneBos);
		} finally {
			currentLock.unlock();
		}
	}

	class BatchStart implements Runnable {

		@Override
		public void run() {
			Collection<StartSceneBo> values = tasks.values();
			for (StartSceneBo sceneBo : values) {
				try {
					UserInfoContext.setUserContext(sceneBo.getUserInfo());
					classroomSceneService.startScene(sceneBo.getGroupId(),
							sceneBo.getAuthContext());
				} catch (Exception e) {
					log.error("Classroom batch start error! group_id=[{}], catch error:",
							sceneBo.getGroupId(), e);
					tasks.remove(sceneBo.getGroupId());
				} finally {
					UserInfoContext.removeUserContext();
				}
			}
		}
	}

	public Boolean isFinishAll() {
		return groupList.isEmpty() && tasks.isEmpty();
	}

}
