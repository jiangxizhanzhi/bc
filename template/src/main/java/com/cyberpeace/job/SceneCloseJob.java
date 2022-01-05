package com.cyberpeace.job;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 场景自动关闭定时任务
 */
@Slf4j
@Component
public class SceneCloseJob {

//	@Scheduled(fixedRate = 15000)
	public void invoke() {
		log.info("SceneCloseJob start");

		closeScene();

		log.info("SceneCloseJob end");
	}

	private void closeScene() {
		// 1、获取待关闭的场景
		List<Object> toCloseScenes = getToCloseScene();

		// 2、逐个调用远程接口，关闭
	}

	/**
	 * 获取待关闭待
	 */
	private List<Object> getToCloseScene() {

		// todo
		return null;
	}
}
