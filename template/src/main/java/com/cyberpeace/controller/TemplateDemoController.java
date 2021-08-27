package com.cyberpeace.controller;

import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.WebSocketContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.dao.entity.Task;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "template的demo")
@RestController
@RequestMapping("/template")
@RequiresAuthentication
public class TemplateDemoController {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@ApiOperation("测试websocket消息发送")
	@GetMapping("/ws")
	public BaseRsp<Task> getTask() {
		WebSocketContext webSocketContext = WebSocketContext.builder()
				.userId(UserInfoContext.getUserContext().getUserId()).businessId("1").build();

		String message = UUID.randomUUID().toString();

		myWebSocketHandler.pushMessage(webSocketContext, message);

		return new BaseRsp();
	}
}
