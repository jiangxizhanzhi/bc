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
 * @date 2024/11/07
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
