//package com.cyberpeace.cs.service.impl;
//
//import java.nio.charset.StandardCharsets;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cyberpeace.common.util.JsonUtil;
//import com.cyberpeace.common.util.SystemPropUtil;
//import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
//import com.cyberpeace.cs.service.GxTaskService;
//import com.cyberpeace.cs.service.context.GxTaskContext;
//
//@Component
//@RabbitListener(queues = "oj.xlrwjs", containerFactory = "gxContainerFactory")
//public class GxConsumer {
//
//	private static final Logger log = LoggerFactory.getLogger("gx");
//
//	@Autowired
//	private GxTaskService gxTaskService;
//
//	@Autowired
//	private GxTaskMapper gxTaskMapper;
//
//	@RabbitHandler
//	public void consumer(byte[] message) {
//
//		JSONObject jsonObject = JSONObject.parseObject(new String(message, StandardCharsets.UTF_8));
//
//		try {
//			processTask(jsonObject);
//		} catch (Exception e) {
//			log.info(String.format("Failed to process message: %s", jsonObject.toString()));
//		}
//
//	}
//
//	private Boolean processTask(JSONObject message) {
//
//		// 判断消息是否处理
//		String software = message.getString("software");
//		if (!StringUtils.equals(software, SystemPropUtil.getProp("SOFT_ID"))) {
//			log.info("Discard irrelevant messages");
//			return true;
//		}
//
//		String taskId = message.getString("taskId");
//		// 校验任务id是否重复
//		if (gxTaskMapper.getByTaskId(taskId) != null) {
//			log.info(String.format("Duplicate messages, taskId: %s", taskId));
//			return false;
//		}
//
//		GxTaskContext gxTaskContext = gxTaskService.getContextByMsg(message);
//		gxTaskService.create(gxTaskContext);
//		log.info(String.format("Successful processing of messages: %s",
//				JsonUtil.toJsonString(message)));
//		return true;
//	}
//}