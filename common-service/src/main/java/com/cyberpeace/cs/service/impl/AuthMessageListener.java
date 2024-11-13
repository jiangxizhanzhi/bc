package com.cyberpeace.cs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

/**
 * 身份验证消息侦听器
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
public class AuthMessageListener implements ChannelAwareMessageListener {

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("mq");

	/**
	 * 注册
	 */
	private static Map<Integer, AuthMessageListener> register = Maps.newHashMap();

	/**
	 * 用户
	 */
	private final String USER = "user_delete";

	/**
	 * 岗位
	 */
	private final String ROLE = "role_delete";

	/**
	 * 岗位更新
	 */
	private final String ROLE_UPDATE = "role_modify";

	/**
	 * 用户更新
	 */
	private final String USER_UPDATE = "user_modify";

	/**
	 * 组织
	 */
	private final String ORG = "org_delete";

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			throw new Error("declareAuthMessageType=[" + declareMessageType() + "] repeat");
		}

		register.put(declareMessageType(), this);
	}

	/**
	 * 在消息中
	 *
	 * @param message 消息
	 * @param channel 渠道
	 * @throws Exception 例外
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String realMessage = new String(message.getBody(), WebConstant.ENCODING);

		// mq日志正常情况不输出，调试的时候再输出
		if (log.isDebugEnabled()) {
			log.debug("MessageListener: {}", realMessage);
		}

		try {
			dispatch(realMessage);
		} catch (Exception e) {
			log.error("MessageListener: dispatch error ", e);
		} finally {
			// 不管数据处理是否成功，都直接ack消息
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}
	}

	/**
	 * 每个子类需要重写该方法，保证返回值唯一
	 *
	 * @return
	 */
	public Integer declareMessageType() {
		return -1;
	}

	/**
	 * 子类定义具体的消息处理方法
	 *
	 * @param users 用户
	 * @return
	 */
	public Boolean onUserDelete(List<String> users) {
		return false;
	}

	/**
	 * 在组织删除时
	 *
	 * @param orgs 组织
	 * @return {@code Boolean }
	 */
	public Boolean onOrgDelete(List<String> orgs) {
		return false;
	}

	/**
	 * 上场景开始过程
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		return false;
	}

	/**
	 * 上场景成功
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	public Boolean onSceneSuccess(String data, String businessId, MqContext context) {
		return false;
	}

	/**
	 * 遣
	 *
	 * @param message 消息
	 * @return boolean
	 */
	private boolean dispatch(String message) {
		JSONObject object;

		try {
			object = JSON.parseObject(message);
		} catch (JSONException e) {
			log.error("can not JSON.parseObject message: {}", message);
			return true;
		}

		String type = object.getString("type");
		List<String> ids = JsonUtil.toJsonObjectArray(object.getString("data"), String.class);

		if (StringUtils.isBlank(type)) {
			log.error("message=[{}] cannot find type ", message);
			return true;
		}
		boolean result = true;
		for (AuthMessageListener realListener : register.values()) {
			String listenerString = realListener.toString();
			log.info("delete_class=[{}] , delete type =[{}] ", listenerString, type);

			try {
				switch (type) {
					case USER:
						realListener.onUserDelete(ids);
						break;
					case ROLE:

						break;
					case ROLE_UPDATE:
						// 更新角色时，消息通知清空缓存
						remoteUserService.clearCache();
						break;
					case USER_UPDATE:
					    // 更新用户的角色时，消息通知清空缓存
						remoteUserService.clearCache();
					break;
					case ORG:
						realListener.onOrgDelete(ids);
						break;
					default:
						break;
				}
			} catch (Exception e) {
				log.error("message=[{}] handler error", message, e);
				result = false;
			}
		}
		return result;
	}

}
