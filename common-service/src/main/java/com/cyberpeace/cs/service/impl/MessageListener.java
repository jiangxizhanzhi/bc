/**
 * ### 代码分析 
 * #### 类概述 
 * - **类名**：`MessageListener` 
 * - **功能**：这是一个消息监听器类，用于处理来自消息队列（如RabbitMQ）的消息。它实现了 `ChannelAwareMessageListener` 接口，支持监听并处理消息。 
 * #### 主要功能模块 
 * 1. **日志记录** 
 * - 使用 `slf4j` 记录日志，便于调试和错误追踪。 
 * 2. **消息分发** 
 * - 实现了 `dispatch` 方法，用于解析消息内容，并根据消息类型调用相应的处理逻辑。 
 * - 支持多种消息类型（如 `SCENE`, `SCENE_ELEMENT`, `SCENE_DELETE`），通过 `switch-case` 结构实现不同的处理逻辑。 
 * 3. **消息处理** 
 * - 提供了多个抽象方法（如 `onSceneDelete`, `onSceneStartProcess`, `onSceneElement`, `onSceneSuccess`），供子类实现具体的业务逻辑。 
 * - 示例中这些方法默认返回 `false`，表明未实现具体逻辑。 
 * 4. **线程上下文管理** 
 * - 使用 `ThreadLocalContext` 管理线程本地变量，例如在处理某些特殊场景时设置白名单更新标志。 
 * 5. **异常处理** 
 * - 对消息解析、业务逻辑执行等可能抛出的异常进行捕获，并记录错误日志。 
 * - 确保无论处理成功与否，都会调用 `channel.basicAck` 方法确认消息已被消费。 
 * 6. **初始化与注册** 
 * - 使用 `@PostConstruct` 注解实现类的初始化逻辑，将当前实例注册到全局的 `register` 映射表中，确保不同消息类型能够正确绑定对应的处理器。 
 * 7. **辅助方法** 
 * - `replaceAccessIp`：替换消息中的访问 IP 地址，通过远程服务完成实际的替换操作。 
 * #### 关键点总结 
 * - **职责清晰**：类专注于消息的接收、解析和分发，具体业务逻辑由子类实现。 
 * - **扩展性强**：通过 `register` 映射表支持动态绑定不同类型的消息处理器。 
 * - **异常安全**：即使处理过程中发生异常，也能保证消息被正确确认，避免重复消费。 
 * - **线程安全**：使用 `ThreadLocal` 管理线程本地变量，确保多线程环境下的安全性。 
 * #### 改进建议 
 * 1. **日志级别调整**： 
 * - 当前日志输出较多，建议根据实际需求调整日志级别，避免性能损耗。 
 * 2. **空指针检查**： 
 * - 在解析 JSON 和处理上下文时，增加更多的空指针检查以增强鲁棒性。 
 * 3. **代码复用**： 
 * - `SCENE` 和 `SCENE_ELEMENT` 的处理逻辑类似，可以考虑提取公共部分减少冗余代码。 
 * 4. **测试覆盖**： 
 * - 增加单元测试和集成测试，确保各分支逻辑的正确性。 
 * 总体而言，这段代码设计合理，结构清晰，适合用于高并发、高性能的消息处理场景。 
**/
package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
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
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

/**
 * 消息侦听器
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class MessageListener implements ChannelAwareMessageListener {

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("mq");

	/**
	 * 注册
	 */
	private static Map<Integer, MessageListener> register = Maps.newHashMap();

	/**
	 * 场景元素
	 */
	private final String SCENE_ELEMENT = "scene_element";

	/**
	 * 场景
	 */
	private final String SCENE = "scene";

	/**
	 * 场景删除
	 */
	private final String SCENE_DELETE = "delete_cscene_instance";

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			throw new Error("declareMessageType=[" + declareMessageType() + "] repeat");
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
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return
	 */
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
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
	 * 上场景元素
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
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
			//演示模式允许修改
			ThreadLocalContext.whiteUpdateThreadLocal.set(true);
			switch (type) {
				case SCENE_DELETE:
					String sceneId = object.getString("data");
					if (null == sceneId || !sceneId.equals(localScene.getMpSceneId())) {
						log.warn(
								"The scene id is null or does not match the database! business_id={}, message_scene_id={}, database_scene_id={}",
								businessId, sceneId, localScene.getMpSceneId());
						break;
					}
					realListener.onSceneDelete(sceneId, businessId, context);
					break;
				case SCENE:
					// 判断是否是已删除场景的推送消息
					String mpSceneId = object.getJSONObject("data").getString("cscene_id");
					if (!localScene.getMpSceneId().equals(mpSceneId)) {
						log.info("mp scene {} has been deleted, no longer belongs to business {}",
								mpSceneId, businessId);
						break;
					}
					WsSceneReportBO sceneReportBO = new WsSceneReportBO("", type,
							object.getJSONObject("data"));
					String sceneContent = JsonUtil.toJsonString(sceneReportBO);
					realListener.onSceneStartProcess(sceneContent, businessId, context);
					break;
				case SCENE_ELEMENT:
					// 判断是否是已删除场景的推送消息
					mpSceneId = object.getJSONObject("data").getString("cscene_id");
					if (!localScene.getMpSceneId().equals(mpSceneId)) {
						log.info("mp scene {} has been deleted, no longer belongs to business {}",
								mpSceneId, businessId);
						break;
					}
					WsSceneReportBO reportBO = new WsSceneReportBO("", type,
							replaceAccessIp(object.getJSONObject("data")));
					String content = JsonUtil.toJsonString(reportBO);
					realListener.onSceneElement(content, businessId, context);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			log.error("message=[{}] handler error", message, e);
			result = false;
		} finally {
			ThreadLocalContext.whiteUpdateThreadLocal.set(false);
		}

		return result;
	}

	/**
	 * 取代访问ip地址
	 *
	 * @param topoData 拓扑数据
	 * @return {@code JSONObject }
	 */
	private JSONObject replaceAccessIp(JSONObject topoData) {
		try {
			JSONObject pluginData = topoData.getJSONObject("scene_element_port_plugin_data");
			if (pluginData == null) {
				return topoData;
			}
			JSONObject elementPorts = pluginData.getJSONObject("cpcloud");
			if (elementPorts == null) {
				return topoData;
			}
			remoteSceneService.replaceAccessIpExe(elementPorts);
		} catch (Exception e) {
			log.error("[dispatch] access ip replacement failed:", e);
		}
		return topoData;
	}

}
