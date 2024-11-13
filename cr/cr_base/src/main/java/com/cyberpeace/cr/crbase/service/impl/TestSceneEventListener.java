package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试场景事件监听器
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
@Slf4j
public class TestSceneEventListener extends MessageListener {

	/**
	 * WebSocket处理器
	 */
	@Autowired
	private MyWebSocketHandler webSocketHandler;

	/**
	 * 业务模板Service
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.TEST_SCENE.getCode();
	}

	/**
	 * 上场景删除
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		BusinessTemplate template = businessTemplateService.getById(businessId);
		businessTemplateService.clearLocal(template);
		return true;
	}

	/**
	 * 上场景开始过程
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	/**
	 * 上场景元素
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);

		return true;
	}

	/**
	 * 推送数据自Web
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 */
	private void pushDataToWeb(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		webSocketHandler.pushMessage(messageContext, true);
	}
}
