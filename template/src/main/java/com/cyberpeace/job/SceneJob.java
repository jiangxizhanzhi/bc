package com.cyberpeace.job;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.GuavaCacheUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 场景自动关闭定时任务
 */
@Slf4j
@Component
public class SceneJob {

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private ClassroomMapper classroomMapper;

	private Cache<String, String> sceneCache = GuavaCacheUtil.newInstance(CommonConstant.FIFTEEN,
			TimeUnit.MINUTES);

	@Scheduled(fixedRate = 15000)
	public void closeScene() {
		log.info("SceneCloseJob start");

		doCloseScene();

		log.info("SceneCloseJob end");
	}

	@Scheduled(fixedRate = 5000)
	public void countDownFifteenMin() {
		log.info("CountDownFifteenMin job start");

		doCountDownFifteenMin();

		log.info("CountDownFifteenMin job end");
	}

	private void doCountDownFifteenMin() {
		Date current = new Date();
		Date fifteenMin = TimeUtil.addMins(current, CommonConstant.FIFTEEN);
		Date fourteenMin = TimeUtil.addMins(current, CommonConstant.ONE);
		List<LocalScene> sceneList = localSceneMapper.selectBetweenEndTime(fourteenMin, fifteenMin);

		// 目前只有实训的课程表
		for (LocalScene localScene : sceneList) {
			try {
				countDown(localScene);
			} catch (Exception e) {
				log.error(
						"Description Scene countdown notification failed to be pushed! local_scene_id=[{}], catch error:",
						localScene.getResourceId(), e);
			}
		}
	}

	private void countDown(LocalScene localScene) {

		// 如果消息已推送 不重复推送
		if (null != sceneCache.getIfPresent(localScene.getResourceId())) {
			return;
		}

		// 课堂场景数据查询
		OjLocalScene ojLocalScene = ojLocalSceneMapper
				.selectById(localScene.getBusinessInstanceId());
		if (localScene.getBusinessCode().intValue() != BusinessCode.CLASSROOM.getCode()
				.intValue()) {
			return;
		}

		// 课堂数据查询
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		if (null == groupInfo) {
			log.error(
					"CountDownFifteenMin job not found group! oj_local_scene_id=[{}], group_id=[{}]",
					ojLocalScene.getResourceId(), ojLocalScene.getBusinessId());
			return;
		}

		Classroom classroom = classroomMapper.selectById(groupInfo.getClassroomId());
		if (null == classroom) {
			log.error(
					"CountDownFifteenMin job not found classroom! oj_local_scene_id=[{}], room_id = [{}]",
					ojLocalScene.getResourceId(), groupInfo.getClassroomId());
			return;
		}

		// 组织推送数据
		JSONObject tipMessage = new JSONObject();
		tipMessage.put(JsonKeyConstant.BUSINESS_ID,
				classroom.getCourseId() + CommonConstant.LEFT_OBLIQUE_LINE + classroom.getLessonId()
						+ CommonConstant.LEFT_OBLIQUE_LINE + "?room_id="
						+ classroom.getResourceId());
		tipMessage.put(JsonKeyConstant.BUSINESS_TYPE, localScene.getBusinessCode());
		tipMessage.put(JsonKeyConstant.SCENE_NAME, localScene.getSceneName());

		// 指定用户推送
		List<String> toUserIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
				String.class);
		toUserIds.forEach(userId -> pushMessageHandler.pushRoomTipsMessage(tipMessage, userId));

		sceneCache.put(localScene.getResourceId(), localScene.getMpSceneId());
	}

	private void doCloseScene() {
		// 1、获取待关闭的场景
		List<LocalScene> toCloseScenes = localSceneMapper.selectByCloseTime(new Date());

		// 2、逐个调用远程接口，关闭
		toCloseScenes.forEach(localScene -> {

			try {
				closeScene(localScene);
			} catch (Exception e) {
				log.error(
						"Failed to automatically close the scene, local_scene_id=[{}], catch error:",
						localScene.getResourceId(), e);
			}
		});
	}

	private void closeScene(LocalScene localScene) {

		// 设置当前线程用户
		UserInfo userInfo = remoteUserService.getById(localScene.getUserId());
		if (null == userInfo) {
			log.error(
					"The scene initiator is not found, use the super administrator instead! user_id=[{}]",
					localScene.getUserId());
			userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		}
		UserInfoContext.setUserContext(userInfo);

		// 调用关闭接口
		log.info(
				"Execute delete scene job start! scene_name=[{}], business_instance_id = [{}], create_user=[{}], ",
				localScene.getSceneName(), localScene.getBusinessInstanceId(),
				userInfo.getUserName());
		try {
			localSceneService.deleteRemoteScene(localScene);
		} catch (Exception e) {
			log.error("Execute delete scene job failed! catch error:", e);
		} finally {
			// 移除用户
			log.info("Execute delete scene job end!");
			UserInfoContext.removeUserContext();
		}
	}

}
