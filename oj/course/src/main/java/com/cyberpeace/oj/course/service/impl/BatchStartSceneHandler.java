package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.StartSceneBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchStartSceneHandler {

	/** 需要启动的总场景*/
	private List<StartSceneBo> groupList;

	/** 已分配的场景*/
	private Map<String, StartSceneBo> tasks = Maps.newHashMap();

	/** 要启动的场景*/
	private List<StartSceneBo> startGroupList = Lists.newArrayList();

	private Lock currentLock = new ReentrantLock();

	/** 批量启动数量**/
	private Integer batchNumber = StringUtils.isNotBlank(SystemPropUtil.getProp("BATCH_NUMBER"))
			? Integer.parseInt(SystemPropUtil.getProp("BATCH_NUMBER"))
			: Integer.parseInt(ConfigUtil.getContextProperty("scene.batch.number"));

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

			StartSceneBo remove = tasks.remove(groupId);
			if (null != remove && startGroupList.size() > CommonConstant.ZERO) {
				startGroupList.remove(remove);
			}
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
			if (!tasks.isEmpty() && tasks.size() == batchNumber) {
				return;
			}

			if (groupList.isEmpty()) {
				return;
			}

			int preDistribution = batchNumber - tasks.size();
			int distribution = preDistribution < groupList.size() ? preDistribution
					: groupList.size();
			for (int i = 0; i < distribution; i++) {
				StartSceneBo sceneBo = groupList.remove(CommonConstant.ZERO.intValue());
				tasks.put(sceneBo.getGroupId(), sceneBo);
				startGroupList.add(sceneBo);
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

	/**
	 * 增加课程表启动的场景
	 * @param userInfo
	 * @param groupInfos
	 */
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

			for (int i = startGroupList.size() - 1; i >= 0; i--) {
				StartSceneBo sceneBo = startGroupList.remove(i);
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
