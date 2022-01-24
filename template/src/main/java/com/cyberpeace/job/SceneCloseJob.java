package com.cyberpeace.job;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	private RemoteSceneService remoteSceneService;

//	@Scheduled(fixedRate = 15000)
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
			SceneOpsContext context = new SceneOpsContext();
			context.setId(localScene.getMpSceneId());

//			remoteSceneService.stopScene();
		});
	}

}
