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
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageListener implements ChannelAwareMessageListener {

	private static Map<String, MessageListener> register = Maps.newHashMap();

	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			throw new Error("declareMessageType=[" + declareMessageType() + "] repeat");
		}

		register.put(declareMessageType(), this);
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		if (message.getMessageProperties().getRedelivered()) {
			log.error("消息已重复处理失败,拒绝再次接收");
			channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
		}

		String realMessage = new String(message.getBody(), WebConstant.ENCODING);

		log.debug("消费者收到消息:{}", realMessage);

		boolean result = dispatch(realMessage);

		// 不管数据处理是否成功，都直接ack消息
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	/**
	 * 每个子类需要重写该方法，保证返回值唯一
	 * @return
	 */
	public String declareMessageType() {
		return "";
	}

	/**
	 * 子类定义具体的消息处理方法
	 * @return
	 */
	public boolean handlerMessage(MqContext context, String data) {
		return false;
	}

	private boolean dispatch(String message) {
		JSONObject object = JSON.parseObject(message);

		String type = object.getString("type");

		if (StringUtils.isBlank(type)) {
			log.error("message=[{}] cannot find type ", message);
			return true;
		}

		if (!register.containsKey(type)) {
			log.error("message=[{}] cannot find type handler", message);
			return true;
		}

		MqContext context = JSON.parseObject(object.getString("context"), MqContext.class);

		boolean result = true;
		try {
			result = register.get(type).handlerMessage(context, object.getString("data"));
		} catch (Exception e) {
			log.error("message=[{}] handler error", message, e);
			result = false;
		}

		return result;
	}
}
