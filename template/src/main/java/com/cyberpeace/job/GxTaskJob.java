package com.cyberpeace.job;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.integratation.model.mp.RemoteUser;
import com.cyberpeace.integratation.service.RemoteTaskService;
import com.cyberpeace.integratation.service.RemoteUserService;

/**
 * 任务定时任务
 */
@Component
public class GxTaskJob {

	private static final Logger log = LoggerFactory.getLogger("gx");

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Autowired
	private RemoteTaskService remoteTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private GxTaskService gxTaskService;

	private String SOFT_ID;

	@PostConstruct
	public void init() {

		SOFT_ID = SystemPropUtil.getProp("SOFT_ID");

	}

	@Scheduled(fixedRate = 15000)
	public void invoke() {
		log.info("GxTaskJob start");

		// 获取新任务
		getTask();

		// 关闭已结束任务
		closeTask();

		log.info("GxTaskJob end");
	}

	/**定期获取共性平台任务*/
	private void getTask() {

		List<String> existTaskIds = gxTaskMapper.listTaskId();

		// 获取所有远程用户id列表
		List<RemoteUser> remoteUsers = remoteUserService.getGxUserList();
		for (RemoteUser remoteUser : remoteUsers) {
			String token = remoteUser.getThirdToken();
			List<JSONObject> userTasks = remoteTaskService.getTasks(remoteUser.getUserId(), SOFT_ID,
					token);
			for (JSONObject userTask : userTasks) {

				String taskId = userTask.getJSONObject("taskBaseData").getString("taskId");

				if (existTaskIds.contains(taskId)) {
					continue;
				}

				GxTaskContext gxTaskContext = gxTaskService.getContextByData(userTask, token);
				gxTaskService.create(gxTaskContext);
			}

		}

	}

	/**定期关闭共性平台任务*/
	private void closeTask() {
		// 1、获取待关闭的任务
		List<GxTask> toCloseTasks = gxTaskMapper.selectByCloseTime(new Date());

		// 2、逐个调用远程接口，关闭
		toCloseTasks.forEach(gxTask -> {
			remoteTaskService.stopExamTask(gxTask.getTaskId(), gxTask.getEventId(),
					gxTask.getDuration());
		});
	}

}
