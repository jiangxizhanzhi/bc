package com.cyberpeace.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 场景自动关闭定时任务
 */
@Slf4j
@Component
public class SceneCloseJob {

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Scheduled(fixedRate = 15000)
	public void invoke() {
		log.info("SceneCloseJob start");

		closeScene();

		log.info("SceneCloseJob end");
	}

	private void closeScene() {
		// 1、获取待关闭的场景
		List<LocalScene> toCloseScenes = localSceneMapper.selectByCloseTime(new Date());

		// 2、逐个调用远程接口，关闭
		toCloseScenes.forEach(localScene -> {

			// 设置当前线程用户
			UserInfo userInfo = remoteUserService.getById(localScene.getUserId());
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

		});
	}

}
