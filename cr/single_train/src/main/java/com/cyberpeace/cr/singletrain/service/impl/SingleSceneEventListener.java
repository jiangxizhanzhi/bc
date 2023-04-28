package com.cyberpeace.cr.singletrain.service.impl;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;

@Service
@Slf4j
public class SingleSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private SingleRoomService singleRoomService;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.SINGLE_TRAINING.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		SingleRoom room = singleRoomService.getByBusinessId(businessId);

		singleRoomService.clearLocal(room);

		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);

		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		try {
			String[] singleBusinessId = StringUtils.split(businessId, "_");
			SingleRoom singleRoom = singleRoomService.getByBusinessTemplateIdAndUserId(singleBusinessId[0], singleBusinessId[1]);
			CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(singleRoom.getResourceId(), singleRoom.getUserId());
			if (crRole != null) {
				data = crEventListenerHelper.filterApplyDevice(data, crRole, businessId);
			}
		} catch (Exception e) {
			// 忽略
		}
		pushDataToWeb(data, businessId);

		return true;
	}

	/**
	 * 场景和节点的状态变更消息统一推送
	 * @param data
	 * @param businessId
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
