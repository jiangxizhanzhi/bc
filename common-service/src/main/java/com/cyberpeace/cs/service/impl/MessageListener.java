package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageListener implements ChannelAwareMessageListener {

	private static Map<Integer, MessageListener> register = Maps.newHashMap();

	private final String SCENE_ELEMENT = "scene_element";

	private final String SCENE = "scene";

	private final String SCENE_DELETE = "delete_cscene_instance";

	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			throw new Error("declareMessageType=[" + declareMessageType() + "] repeat");
		}

		register.put(declareMessageType(), this);
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		// if (message.getMessageProperties().getRedelivered()) {
		// log.error("消息已重复处理,拒绝再次接收");
		// channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		// }

		String realMessage = new String(message.getBody(), WebConstant.ENCODING);

		log.error("MessageListener: {}", realMessage);

		boolean result = dispatch(realMessage);

		// 不管数据处理是否成功，都直接ack消息
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	/**
	 * 每个子类需要重写该方法，保证返回值唯一
	 * @return
	 */
	public Integer declareMessageType() {
		return -1;
	}

	/**
	 * 子类定义具体的消息处理方法
	 * @return
	 */
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		return false;
	}

	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		return false;
	}

	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		return false;
	}

	private boolean dispatch(String message) {
		JSONObject object = JSON.parseObject(message);

		String type = object.getString("type");

		if (StringUtils.isBlank(type)) {
			log.error("message=[{}] cannot find type ", message);
			return true;
		}

		MqContext context = JSON.parseObject(object.getString("context"), MqContext.class);

		String passThroughString = context.getPassThrough();
		JSONObject passThroughJSON = JSON.parseObject(passThroughString, JSONObject.class);
		if (StringUtils.isBlank(passThroughString) || null == passThroughJSON) {
			log.error("message=[{}] handler error, passThrough is null", message);
			return false;
		}

		String businessId = passThroughJSON.getString(WebConstant.PASS_THROUGH_BUSINESS_ID);
		if (StringUtils.isBlank(businessId)) {
			log.error("message=[{}] handler error, businessId in passThrough is null", message);
			return false;
		}

		LocalSceneMapper localSceneMapper = SpringContextUtil
				.getBeanByClass(LocalSceneMapper.class);
		LocalScene localScene = localSceneMapper.getByBusinessInstanceId(businessId);
		if (null == localScene) {
			log.error("message=[{}] businessId=[{}] handler error, localScene is null", message,
					businessId);
			return true;
		}

		if (!register.containsKey(localScene.getBusinessCode())) {
			log.error("message=[{}] cannot find type handler", message);
			return true;
		}

		MessageListener realListener = register.get(localScene.getBusinessCode());

		boolean result = true;
		try {
			WsSceneReportBO reportBO = new WsSceneReportBO("", type, object.getJSONObject("data"));
			String content = JsonUtil.toJsonString(reportBO);

			switch (type) {
				case SCENE_DELETE:
					realListener.onSceneDelete(object.getString("data"), businessId, context);
					break;
				case SCENE:
					realListener.onSceneStartProcess(content, businessId, context);
					break;
				case SCENE_ELEMENT:
					realListener.onSceneElement(content, businessId, context);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			log.error("message=[{}] handler error", message, e);
			result = false;
		}

		return result;
	}

}
