/**
 * 这段代码定义了一个名为 `TestController` 的 Spring MVC 控制器类，用于处理与测试相关的 HTTP 请求。以下是对代码的简要分析： 
 * 1. **类注解**： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/test")`：将该控制器的所有请求映射到 `/test` 路径下。 
 * - `@RequiresAuthentication`：确保只有经过身份验证的用户才能访问这些接口。 
 * 2. **依赖注入**： 
 * - 使用了 `@Autowired` 注解来自动装配三个服务类：`RemoteTopoService`, `RemoteSceneService`, 和 `RemoteLogService`。 
 * 3. **成员变量**： 
 * - `testTopoId`：一个字符串类型的成员变量，存储了一个固定的拓扑ID。 
 * 4. **方法分析**： 
 * - `createTopo()`：通过调用 `remoteTopoService.copyTopo()` 方法复制拓扑，并返回一个包含结果的 `BaseRsp` 对象。 
 * - `topoStatistic()`：获取指定拓扑的统计数据（`TopoDigest`），并将其封装在 `BaseRsp` 对象中返回。 
 * - `convertHtml()`：接收 JSON 格式的请求体（`DemoReq1`），调用工具类 `HtmlToPdfUtil` 将 HTML 文件转换为 PDF 文件，然后返回布尔值的结果。 
 * - `addMpLog()`：记录中台日志，使用当前用户的上下文信息（`UserInfoContext.getUserContext()`）以及操作模块和类型等参数。 
 * 5. **未实现的方法**： 
 * - 注释掉的 `startScene()` 和 `stopScene()` 方法展示了如何启动或停止场景服务，但目前尚未实现具体的逻辑。 
 * 总体来说，这个控制器主要负责处理与拓扑、场景及日志相关的业务逻辑，并且提供了多种不同的 API 接口供外部调用。 
**/
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
 * @date 2025/05/28
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
	 * @return 基础响应
	 * @throws
	 * @see BaseRsp
	 */
	@PostMapping("/copytopo")
	public BaseRsp createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp(result);
	}

	/**
	 * 拓扑统计
	 *
	 * @return 基础响应
	 * @throws
	 * @see BaseRsp
	 */
	@GetMapping("topoStatistic")
	public BaseRsp topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp(digest);
	}

	/**
	 * 转换 HTML
	 *
	 * @param demoReq 演示请求体
	 * @return base rsp< 布尔值>
	 * @throws
	 * @see DemoReq1
	 * @see BaseRsp<Boolean>
	 */
	@PostMapping
	public BaseRsp<Boolean> convertHtml(@RequestBody DemoReq1 demoReq) {
		boolean result = HtmlToPdfUtil.convert(demoReq.getHtmlFilePath(), demoReq.getPdfFilePath());

		return new BaseRsp(result);
	}
	//
	// @GetMapping("/startScene")
	// public BaseRsp<String> startScene() {
	// String sceneId = remoteSceneService.startScene(, null);
	// return new BaseRsp<>(sceneId);
	// }

	// @GetMapping("/stopScene")
	// public BaseRsp<Boolean> stopScene() {
	// Boolean result =
	// remoteSceneService.stopScene("b3304280b0a14d5aa7cd4dcf03ddd8f", null);
	//
	// return new BaseRsp<>(result);
	// }

	/**
	 * 新增中台日志
	 *
	 * @return 基础响应
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
