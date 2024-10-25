package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.service.SpaceIndexService;
import com.cyberpeace.space.service.context.index.IndexFrame;
import com.cyberpeace.space.service.context.index.IndexKnowledge;
import com.cyberpeace.space.service.context.index.IndexTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "知识库-知识空间首页")
@RestController
@RequestMapping("/space/index/")
@RequiresAuthentication
public class SpaceIndexController {

	@Autowired
	private SpaceIndexService indexService;

	@ApiOperation("防御知识库统计")
	@GetMapping("/knowledge")
	public BaseRsp<IndexKnowledge> getKRepository() {
		IndexKnowledge res = indexService.getKnowledgeRepositoryStatistics();
		return new BaseRsp(res);
	}

	@ApiOperation("训练工具集数据统计")
	@GetMapping("/tool")
	public BaseRsp<IndexTool> getTRepository() {
		IndexTool res = indexService.getToolStatistics();
		return new BaseRsp(res);
	}

	@ApiOperation("防御行动框架")
	@GetMapping("/frame")
	public BaseRsp<IndexFrame> getF() {
		IndexFrame res = indexService.getFrameIndex();
		return new BaseRsp(res);
	}

}
