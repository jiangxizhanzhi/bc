package com.cyberpeace.cr.singletrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 单场景事件听者
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MessageListener
 * @since 1.0.0
 */
@Service
@Slf4j
public class SingleSceneEventListener extends MessageListener {

    /**
     * 我 Web 套接字处理程序
     *
     * @see MyWebSocketHandler
     */
    @Autowired
	private MyWebSocketHandler myWebSocketHandler;

    /**
     * 单房间Service
     *
     * @see SingleRoomService
     */
    @Autowired
	private SingleRoomService singleRoomService;

    /**
     * 声明消息类型
     *
     * @return {@code Integer }
     * @throws
     * @see Integer
     */
    @Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.SINGLE_TRAINING.getCode();
	}

    /**
     * 上场景删除
     *
     * @param data       数据
     * @param businessId 业务id
     * @param context    上下文
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
     */
    @Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		SingleRoom room = singleRoomService.getByBusinessId(businessId);

		singleRoomService.clearLocal(room);

		return true;
	}

    /**
     * 上场景开始过程
     *
     * @param data       数据
     * @param businessId 业务id
     * @param context    上下文
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
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
     * @throws
     * @see String
     * @see String
     * @see MqContext
     * @see Boolean
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
     * @throws
     * @see String
     * @see String
     */
    private void pushDataToWeb(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}
}
