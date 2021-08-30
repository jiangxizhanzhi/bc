package com.cyberpeace.controller;

import java.util.Date;
import java.util.UUID;

import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.model.DemoWsMessage;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.model.DemoWsMessageIdentifier;
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
		DemoWsMessageIdentifier identifier = DemoWsMessageIdentifier.builder().courseId("courseId")
				.lessonId("lessonId").build();
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		DemoWsMessage data = DemoWsMessage.builder().code(1).message("test message")
				.timestamp(new Date()).build();

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, false);

		return new BaseRsp();
	}
}
