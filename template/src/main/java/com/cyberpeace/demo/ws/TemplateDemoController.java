package com.cyberpeace.demo.ws;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.model.DemoWsMessage;
import com.cyberpeace.model.DemoWsMessageIdentifier;
import com.cyberpeace.rtks.dao.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 模板演示Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "template的demo")
@RestController
@RequestMapping("/template")
@RequiresAuthentication
public class TemplateDemoController {

	/**
	 * 我的WebSocket处理器
	 *
	 * @see MyWebSocketHandler
	 */
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 路径 1
	 *
	 * @see String
	 */
	String path1 = "/ws";

	/**
	 * 路径 2
	 *
	 * @see String
	 */
	String path2 = "/ws/course";

	/**
	 * 发送 msg1
	 *
	 * @return {@code BaseRsp<Task> }
	 * @throws
	 * @see BaseRsp<Task>
	 */
	@ApiOperation("测试websocket消息发送1")
	@GetMapping("/ws")
	public BaseRsp<Task> sendMsg1() {
		DemoWsMessageIdentifier identifier = DemoWsMessageIdentifier.builder().courseId("12")
				.lessonId("34").build();
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(path1);

		DemoWsMessage data = DemoWsMessage.builder().code(1).message("test message1")
				.timestamp(new Date()).build();

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, false);

		return new BaseRsp<>();
	}

	/**
	 * 发送 msg2
	 *
	 * @return {@code BaseRsp<Task> }
	 * @throws
	 * @see BaseRsp<Task>
	 */
	@ApiOperation("测试websocket消息发送2")
	@GetMapping("/ws1")
	public BaseRsp<Task> sendMsg2() {
		WsMessageIdentifier identifier = new WsMessageIdentifier();
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(path2);

		DemoWsMessage data = DemoWsMessage.builder().code(1).message("test message2")
				.timestamp(new Date()).build();

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);

		return new BaseRsp<>();
	}
}
