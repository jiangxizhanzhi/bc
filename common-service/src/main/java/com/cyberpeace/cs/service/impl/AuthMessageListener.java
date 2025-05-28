/**
 * 这段代码实现了一个基于RabbitMQ的消息监听器`AuthMessageListener`，主要用于处理身份验证相关的消息。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **消息监听**：监听RabbitMQ队列中的消息，并根据消息类型进行相应的处理。 
 * 2. **消息分发**：通过`dispatch`方法解析消息内容，并根据消息类型调用对应的方法处理。 
 * 3. **注册机制**：通过静态变量`register`管理不同类型的监听器实例，确保每个消息类型只有一个对应的监听器。 
 * 4. **异常处理**：在消息处理过程中捕获异常，并记录日志，同时确保消息被正确确认（ack）。 
 * ### 关键点分析： 
 * - **日志记录**：使用SLF4J记录日志，包括调试、错误和信息级别的日志。 
 * - **消息解析**：使用`JSON`库解析消息内容，提取`type`字段来决定如何处理消息。 
 * - **消息类型处理**： 
 * - `USER_DELETE` 和 `ORG_DELETE`：分别调用`onUserDelete`和`onOrgDelete`方法处理。 
 * - `ROLE_UPDATE` 和 `USER_UPDATE`：调用远程服务`remoteUserService`清除缓存。 
 * - 其他类型默认处理。 
 * - **初始化**：通过`@PostConstruct`注解的`init`方法，在Spring容器初始化完成后注册监听器实例。 
 * ### 优点： 
 * - **模块化设计**：通过抽象出不同的消息处理方法，便于扩展新的消息类型。 
 * - **容错性**：即使某个消息处理失败，整体流程不会中断，所有消息都会被确认。 
 * ### 改进建议： 
 * - **多态性**：可以考虑将消息处理逻辑抽象为接口或抽象类，进一步增强代码的可维护性和扩展性。 
 * - **并发处理**：当前代码在处理消息时是顺序执行的，可能需要根据实际需求优化为并发处理以提高性能。 
 * - **日志级别调整**：某些日志可能过于冗长，可以根据实际情况调整日志级别。 
 * 总体而言，这段代码实现了基本的消息监听和处理功能，具有良好的扩展性和一定的容错能力。 
**/
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
 * @date 2024/11/08
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
		// MessageListener realListener = register.;
		boolean result = true;
		for (Integer key : register.keySet()) {
			AuthMessageListener realListener = register.get(key);
			log.info("delete_class=[{}] , delete type =[{}] ", realListener.toString(), type);

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
