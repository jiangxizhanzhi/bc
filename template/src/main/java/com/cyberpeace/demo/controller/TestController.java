package com.cyberpeace.demo.controller;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;

/**
 * 测试Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
@RequiresAuthentication
public class TestController {

	/**
	 * 远程拓扑Service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 测试拓扑id
	 *
	 * @see String
	 */
	String testTopoId = "1dd035784f5b44869c4e96aa07c31fd5";

	/**
	 * 创建拓扑
	 *
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see BaseRsp<String>
	 */
	@PostMapping("/copytopo")
	public BaseRsp<String> createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp<>(result);
	}

	/**
	 * 拓扑统计
	 *
	 * @return {@code BaseRsp<TopoDigest> }
	 * @throws
	 * @see BaseRsp<TopoDigest>
	 */
	@GetMapping("topoStatistic")
	public BaseRsp<TopoDigest> topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp<>(digest);
	}

	/**
	 * 转换 HTML
	 *
	 * @param demoReq 演示请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see DemoReq1
	 * @see BaseRsp<Boolean>
	 */
	@PostMapping
	public BaseRsp<Boolean> convertHtml(@RequestBody DemoReq1 demoReq) {
		boolean result = HtmlToPdfUtil.convert(demoReq.getHtmlFilePath(), demoReq.getPdfFilePath());

		return new BaseRsp<>(result);
	}

	/**
	 * 新增中台日志
	 *
	 * @return {@code BaseRsp }
	 * @throws
	 * @see BaseRsp
	 */
	@PostMapping("/add_mp_log")
	public BaseRsp addMpLog() {

		UserInfo userInfo = UserInfoContext.getUserContext();
		remoteLogService.addCrLog(userInfo, OperationModule.SINGLE_TRAIN, OperationType.STOP,"测试对象");

		return new BaseRsp();
	}

}
