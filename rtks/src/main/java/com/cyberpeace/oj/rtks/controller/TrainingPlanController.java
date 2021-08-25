package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.TrainingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "TRKS-训练计划管理")
@RestController
@RequestMapping("/rtks/training-plan")
@RequiresAuthentication
public class TrainingPlanController {

	@Autowired
	private LevelService levelService;

	@Autowired
	private TrainingPlanService trainingPlanService;

	@Autowired
	private FrameController frameController;
	// @ApiOperation("框架列表查询")
	// @GetMapping()
	// public BaseRsp<ListRsp<FrameOutput>> getFrames(ListReq<Level> listReq) {
	// IPage<Level> page = trainingPlanService.getFrames(listReq.toPageParam(),
	// listReq.getKeyword());
	// return PageUtil2.getListResult(page, FrameOutput::new);
	// }

	@ApiOperation("知识技能体系")
	@GetMapping("/ability")
	public BaseRsp<Map> skill() {
		Map<String, List<SkillRes>> result = trainingPlanService.getSkillTree();

		return new BaseRsp<>(result);
	}

	@ApiOperation("职业框架管理")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> tree(
			@ApiParam(name = "resource_id", value = "框架id", required = true) String resource_id) {

		LevelTreeOutput frame = frameController.getTree(resource_id).data;
		trainingPlanService.parseTree(frame);

		return new BaseRsp<>(frame);
	}
}
