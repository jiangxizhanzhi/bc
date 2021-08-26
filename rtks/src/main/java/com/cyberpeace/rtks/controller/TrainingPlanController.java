package com.cyberpeace.rtks.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.TrainingPlanService;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	private RtksStatisticService rtksStatisticService;

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

	@GetMapping("/test")
	@ApiOperation("测试接口")
	public BaseRsp getRtksstatistic() {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<RtksRadarBo> liuleng = rtksStatisticService
				.calculateRadar("e42615d51086cf7e1abbbaee82a593f9", userId);
		return new BaseRsp();
	}

	@ApiOperation("人才能力图谱")
	@GetMapping("/roleMap")
	public BaseRsp<RoleDetailRes> getRoleOutputByRoleId(
			@ApiParam(name = "resource_id", value = "岗位id", required = true) String resource_id) {
		RoleDetailRes roleOutput = trainingPlanService.getRoleOutputByRoleId(resource_id);

		return new BaseRsp<RoleDetailRes>(roleOutput);
	}

}
