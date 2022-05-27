package com.cyberpeace.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.integratation.service.RemoteTaskService;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务定时任务
 */
@Slf4j
@Component
public class TaskCloseJob {

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private RemoteTaskService remoteTaskService;

	@Scheduled(fixedRate = 15000)
	public void invoke() {
		log.info("TaskCloseJob start");
		closeTask();
		log.info("TaskCloseJob end");
	}

	private void closeTask() {
		// 1、获取待关闭的任务
		List<GxTask> toCloseTasks = gxTaskMapper.selectByCloseTime(new Date());

		// 2、逐个调用远程接口，关闭
		toCloseTasks.forEach(gxTask -> {
			remoteTaskService.stopExamTask(gxTask.getTaskId(), gxTask.getEventId(),
					gxTask.getDuration());
			log.info("关闭任务成功:" + gxTask.getName());

		});
	}

}
