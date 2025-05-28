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
