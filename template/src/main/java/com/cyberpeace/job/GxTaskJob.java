package com.cyberpeace.job;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.dao.entity.GxTask;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
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

	private Integer EXAM_ID = 1;

	private Integer CLASSROOM_ID = 2;

	@PostConstruct
	public void init() {
		SOFT_ID = SystemPropUtil.getProp("SOFT_ID");
	}

	@Scheduled(fixedRate = 15000)
	public void invoke() {
		log.info("GxTaskJob start");

		// 关闭已结束任务
		closeTask();

		log.info("GxTaskJob end");
	}

	/**定期关闭共性平台任务*/
	private void closeTask() {
		// 1、获取待关闭的任务
		List<GxTask> toCloseTasks = gxTaskMapper.selectByCloseTime(new Date());

		// 2、逐个调用远程接口，关闭
		for (GxTask gxTask : toCloseTasks) {

			// 关闭考试任务
			if (EXAM_ID.equals(gxTask.getEventType())) {
				remoteTaskService.stopExamTask(gxTask.getTaskId(), gxTask.getEventId(),
						gxTask.getDuration());
			}

			// 关闭课程表任务
			if (CLASSROOM_ID.equals(gxTask.getEventType())) {
				// 获取所有远程用户id列表
				List<RemoteUser> remoteUsers = remoteUserService.getGxUserList();
				for (RemoteUser remoteUser : remoteUsers) {
					if (gxTaskService.finishClassroomTask(gxTask, null,
							remoteUser.getThirdToken())) {
						break;
					}
				}
			}
		}

	}

}
