package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 红蓝对抗场景事件监听器
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
@Slf4j
public class RbSceneEventListener extends MessageListener {

	/**
	 * WebSocket处理器
	 */
	@Autowired
	private MyWebSocketHandler webSocketHandler;

	/**
	 * 红蓝对抗房间Service
	 */
	@Autowired
	private RbRoomService rbRoomService;

	/**
	 * 红蓝对抗场景Service
	 */
	@Autowired
	private RbSceneService rbSceneService;

	/**
	 * 靶场事件监听器助手
	 */
	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.RED_VS_BLUE.getCode();
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
		RbRoom room = rbRoomService.getById(businessId);
		rbSceneService.clearLocal(room);
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
		// get roles
		List<CrRole> roles = crRoleService.getRolesByRoom(businessId,
				BusinessType.RED_VS_BLUE.getCode());
		// parse message
		WsSceneReportBO wsSceneReportBO = JsonUtil.toJsonObject(data, WsSceneReportBO.class);
		JSONObject jsonData = wsSceneReportBO.getData().getData();
		// 区分场景节点消息推送和节点登录信息推送
		if (jsonData.containsKey("call_action")) {
			pushDataToWeb(data, businessId);
			log.info("call action push data to web, businessId =[{}]", businessId);
			return true;
		}
		String nodeId = jsonData.getJSONObject("scene_element").getString("id");
		for (CrRole role : roles) {
			String filteredData = crEventListenerHelper.filterDataByRole(role, data, nodeId);
			if (filteredData == null) {
				continue;
			}
			pushDataToWebByUserIds(filteredData, businessId, role.getUserIdList());
		}
		return true;
	}

	/**
	 * 场景消息统一推送
	 *
	 * @param data
	 * @param businessId 业务id
	 */
	private void pushDataToWeb(String data, String businessId) {
		WebSocketMessageContext<CrSceneWsMessageIdentifier, Object> messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		webSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给某个角色的所有用户
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param userIds    用户id列表
	 */
	private void pushDataToWebByUserIds(String data, String businessId, List<String> userIds) {
		WebSocketMessageContext<CrSceneWsMessageIdentifier, Object> messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		webSocketHandler.pushMessageToUsers(messageContext, userIds);
	}
}
