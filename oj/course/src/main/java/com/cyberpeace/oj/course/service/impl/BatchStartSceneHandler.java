package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
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

/**
 * 批量启动场景处理器
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
public class BatchStartSceneHandler {

	/**
	 * 需要启动的总场景
	 */
	private List<StartSceneBo> groupList;

	/**
	 * 已分配的场景
	 */
	private Map<String, StartSceneBo> tasks = Maps.newHashMap();

	/**
	 * 要启动的场景
	 */
	private List<StartSceneBo> startGroupList = Lists.newArrayList();

	/**
	 * 当前锁定
	 */
	private Lock currentLock = new ReentrantLock();

	/**
	 * 批量启动数量
	 */
	private Integer batchNumber = StringUtils.isNotBlank(SystemPropUtil.getProp("BATCH_NUMBER"))
			? Integer.parseInt(SystemPropUtil.getProp("BATCH_NUMBER"))
			: Integer.parseInt(ConfigUtil.getContextProperty("scene.batch.number"));

	/**
	 * 课堂场景Service
	 */
	private ClassroomSceneService classroomSceneService;

	/**
	 * 批量启动场景处理器
	 *
	 * @param userInfo     用户信息
	 * @param groupInfos   组信息
	 * @param sceneService 场景Service
	 */
	public BatchStartSceneHandler(UserInfo userInfo, List<ClassroomGroupInfo> groupInfos,
			ClassroomSceneService sceneService) {
		groupList = initStartBo(userInfo, groupInfos);
		this.classroomSceneService = sceneService;
	}

	/**
	 * 场景启动完成
	 *
	 * @param groupId 组id
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
			int distribution = Math.min(preDistribution, groupList.size());
			for (StartSceneBo sceneBo : groupList.subList(0, distribution)) {
				tasks.put(sceneBo.getGroupId(), sceneBo);
				startGroupList.add(sceneBo);
			}
			groupList=groupList.subList(distribution,groupList.size());

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

	/**
	 * 初始化启动Bo
	 *
	 * @param userInfo   用户信息
	 * @param groupInfos 组信息
	 * @return {@code List<StartSceneBo> }
	 */
	private List<StartSceneBo> initStartBo(UserInfo userInfo, List<ClassroomGroupInfo> groupInfos) {
		return StreamApiUtils.map(groupInfos, groupInfo -> {
			StartSceneBo startSceneBo = new StartSceneBo();
			startSceneBo.setAuthContext(AuthContextUtils.constructAuth(userInfo, false));
			startSceneBo.setGroupId(groupInfo.getResourceId());
			startSceneBo.setUserInfo(userInfo);
			startSceneBo.setClassroomId(groupInfo.getClassroomId());
			startSceneBo.setLanguage(CommonContext.getLanguage());
			return startSceneBo;
		});
	}

	/**
	 * 增加课程表启动的场景
	 *
	 * @param userInfo
	 * @param groupInfos 组信息
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
					CommonContext.setLanguage(sceneBo.getLanguage());
					classroomSceneService.startScene(sceneBo.getGroupId(),
							sceneBo.getAuthContext());

				} catch (RemoteServiceException e) {
					log.error("Classroom batch start error! group_id=[{}], catch error:",
							sceneBo.getGroupId(), e);
					classroomSceneService.toClassroomMessage(sceneBo, OjEvent.START_SCENE_ERROR,
							e.getMessage());
					classroomSceneService.clearBatchStartScene(sceneBo.getClassroomId());

				} catch (Exception e) {
					log.error("Classroom batch start error! group_id=[{}], catch error:",
							sceneBo.getGroupId(), e);
					classroomSceneService.toClassroomMessage(sceneBo, OjEvent.START_SCENE_ERROR,
							ERROR_START_SCENE.ERROR_MESSAGE.getMsg());
					classroomSceneService.clearBatchStartScene(sceneBo.getClassroomId());

				} finally {
					UserInfoContext.removeUserContext();
					CommonContext.removeLanguage();
				}
			}
		}
	}

	/**
	 * 是否完成全部
	 *
	 * @return {@code Boolean }
	 */
	public Boolean isFinishAll() {
		return groupList.isEmpty() && tasks.isEmpty();
	}

	enum ERROR_START_SCENE implements BaseI18n {

		ERROR_MESSAGE("场景启动失败", "Scenario Startup failure.");

		private String chinese;

		private String english;

		ERROR_START_SCENE(String chinese, String english) {
			this.chinese = chinese;
			this.english = english;
		}

		@Override
		public String getChinese() {
			return chinese;
		}

		@Override
		public String getEnglish() {
			return english;
		}
	}

}
