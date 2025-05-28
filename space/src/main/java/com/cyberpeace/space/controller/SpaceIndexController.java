/**
 * 这段代码定义了一个名为 `SpaceIndexController` 的 Spring MVC 控制器类，用于处理与知识库相关的 HTTP 请求。以下是代码的主要功能和结构分析： 
 * 1. **类注解**： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/space/index/")`：为所有方法指定基础路径 `/space/index/`。 
 * - `@RequiresAuthentication`：确保只有经过身份验证的用户才能访问这些接口。 
 * 2. **依赖注入**： 
 * - 使用 `@Autowired` 注入了 `SpaceIndexService`，该服务负责处理具体的业务逻辑。 
 * 3. **方法分析**： 
 * - `getKRepository()`：提供知识库统计信息，返回 `BaseRsp<IndexKnowledge>`。 
 * - `getKChart()`：返回知识图谱数据，返回 `BaseRsp<KnowledgeChart>`。 
 * - `getTRepository()`：提供训练工具集的数据统计，返回 `BaseRsp<IndexTool>`。 
 * - `getF()`：返回防御行动框架数据，返回 `BaseRsp<IndexFrame>`。 
 * - `getAiUrl()`：获取人工智能相关网址，返回 `BaseRsp<String>`。 
 * 4. **Swagger 文档支持**： 
 * - 使用了 `io.swagger.annotations` 提供的注解来生成 API 文档，方便前端开发者理解接口用途。 
 * 5. **安全性**： 
 * - 所有方法都需要通过 Apache Shiro 的认证检查 (`@RequiresAuthentication`)。 
 * 总结来说，这个控制器主要负责提供关于知识库、工具集、框架以及人工智能相关资源的各种统计数据和信息，且每个方法都对应一个特定的功能点，并通过 Swagger 注解增强了接口的可读性和文档化程度。 
**/
package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.service.SpaceIndexService;
import com.cyberpeace.space.service.context.index.IndexFrame;
import com.cyberpeace.space.service.context.index.IndexKnowledge;
import com.cyberpeace.space.service.context.index.IndexTool;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间编号Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Api(tags = "知识库-知识空间首页")
@RestController
@RequestMapping("/space/index/")
@RequiresAuthentication
public class SpaceIndexController {

	/**
	 * 编号Service
	 *
	 * @see SpaceIndexService
	 */
	@Autowired
	private SpaceIndexService indexService;

	/**
	 * 获取krepository 仓库
	 *
	 * @return 基础rsp<编号知识>
	 * @throws
	 * @see BaseRsp<IndexKnowledge>
	 */
	@ApiOperation("防御知识库统计")
	@GetMapping("/knowledge")
	public BaseRsp<IndexKnowledge> getKRepository() {
		IndexKnowledge res = indexService.getKnowledgeRepositoryStatistics();
		res.calSum();
		return new BaseRsp<>(res);
	}

	/**
	 * 获取kchart
	 *
	 * @return 基础RSP<知识点图表>
	 * @throws
	 * @see BaseRsp<KnowledgeChart>
	 */
	@ApiOperation("知识图谱")
	@GetMapping("/knowledge_chart")
	public BaseRsp<KnowledgeChart> getKChart() {
		KnowledgeChart res = indexService.getKChart();
		return new BaseRsp<>(res);
	}

	/**
	 * 获取trepository 仓库
	 *
	 * @return 基础RSP<编号工具>
	 * @throws
	 * @see BaseRsp<IndexTool>
	 */
	@ApiOperation("训练工具集数据统计")
	@GetMapping("/tool")
	public BaseRsp<IndexTool> getTRepository() {
		IndexTool res = indexService.getToolStatistics();
		return new BaseRsp<>(res);
	}

	/**
	 * getf
	 *
	 * @return 基础RSP<编号框架>
	 * @throws
	 * @see BaseRsp<IndexFrame>
	 */
	@ApiOperation("防御行动框架")
	@GetMapping("/frame")
	public BaseRsp<IndexFrame> getF() {
		IndexFrame res = indexService.getFrameIndex();
		return new BaseRsp<>(res);
	}

	/**
	 * 获取人工智能网址
	 *
	 * @return base rsp< 字符串>
	 * @throws
	 * @see BaseRsp<String>
	 */
	@ApiOperation("获取大模型地址")
	@GetMapping("/ai")
	public BaseRsp<String> getAiUrl() {
		String aiUrl = indexService.getAiUrl();
		return new BaseRsp<>(aiUrl);
	}

}
