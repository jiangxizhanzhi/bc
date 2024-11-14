package com.cyberpeace.cr.singletrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 单场景事件监听器
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
@Slf4j
public class SingleSceneEventListener extends MessageListener {

	/**
	 * 我的WebSocket处理器
	 */
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 单房间Service
	 */
	@Autowired
	private SingleRoomService singleRoomService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.SINGLE_TRAINING.getCode();
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

		SingleRoom room = singleRoomService.getByBusinessId(businessId);

		singleRoomService.clearLocal(room);

		return true;
	}

	/**
	 * 上场景启动过程
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
	 * 场景和节点的状态变更消息统一推送
	 *
	 * @param data
	 * @param businessId 业务id
	 */
	private void pushDataToWeb(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}
}
