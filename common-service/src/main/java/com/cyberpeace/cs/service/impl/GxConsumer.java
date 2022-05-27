package com.cyberpeace.cs.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.cs.service.GxTaskService;
import com.cyberpeace.cs.service.context.GxTaskContext;
import com.cyberpeace.integratation.service.RemoteTaskService;

@Component
@RabbitListener(queues = "oj.xlrwjs", containerFactory = "gxContainerFactory")
public class GxConsumer {

	private static final Logger log = LoggerFactory.getLogger("gxMq");

	@Autowired
	private GxTaskService gxTaskService;

	@Autowired
	private RemoteTaskService remoteTaskService;

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@RabbitHandler
	public void consumer(byte[] message) {

		JSONObject jsonObject = JSONObject.parseObject(new String(message, StandardCharsets.UTF_8));

		try {
			processTask(jsonObject);
		} catch (Exception e) {
			log.info(String.format("Failed to process message: %s", jsonObject.toString()));
		}

	}

	private Boolean processTask(JSONObject message) {

		// 判断消息是否处理
		String software = message.getString("software");
		if (!StringUtils.equals(software, SystemPropUtil.getProp("SOFT_ID"))) {
			log.info("Discard irrelevant messages");
			return true;
		}

		String taskId = message.getString("taskId");
		// 校验任务id是否重复
		if (gxTaskMapper.getByTaskId(taskId) != null) {
			log.info(String.format("Duplicate messages, taskId: %s", taskId));
			return false;
		}

		// 提取原始数据
		String taskName = message.getString("taskName");
		String trainContent = message.getString("trainContent");
		String target = (String) message.getOrDefault("target", "");
		Integer trainTime = message.getInteger("trainTime");
		Integer trainLength = message.getInteger("trainLength");
		String token = message.getString("token");

		// 处理原始数据
		Double duration = MathUtils.divide((double) trainLength, 60.00);
		Date startTime = new Date(trainTime);

		// 构造任务数据
		GxTaskContext gxTaskContext = new GxTaskContext();
		gxTaskContext.setTaskId(taskId);
		gxTaskContext.setName(taskName);
		gxTaskContext.setContent(trainContent);
		gxTaskContext.setTarget(target);
		gxTaskContext.setDuration(duration);
		gxTaskContext.setStartTime(startTime);
		gxTaskContext.setEndTime(TimeUtil.addMins(startTime, trainLength));

		String trainees = remoteTaskService.getTaskUsers(taskId, token);
		gxTaskContext.setTrainees(trainees);
		gxTaskService.create(gxTaskContext);
		log.info(String.format("Successful processing of messages: %s", message.toString()));
		return true;
	}
}