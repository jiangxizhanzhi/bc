package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAllTimeLineRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskStatisticsRes;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTimeNodeRsp;
import com.cyberpeace.cr.evaluation.service.EvalInstanceStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "靶场测评-试验任务-复盘")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/test/playback")
public class EvalPlayBackController {

	@Autowired
	private EvalInstanceStepService evalInstanceStepService;

	@ApiOperation("查询复盘事件列表")
	@GetMapping("/time_nodes")
	public BaseRsp<List<EvalTimeNodeRsp>> getPlaybackTimeNodes(@ApiParam(name = "instance_id", value = "实例id",
			required = true) @RequestParam("instance_id") String instanceId) {
		return new BaseRsp<>(evalInstanceStepService.getPlaybackTime(instanceId));
	}

	@ApiOperation("复盘时间轴")
	@GetMapping("/time_line")
	public BaseRsp<EvalAllTimeLineRsp> getTimeLine(@ApiParam(name = "instance_id", value = "实例id",
			required = true) @RequestParam("instance_id") String instanceId) {
//		EvalTimeLineRsp lineRsp1 = new EvalTimeLineRsp("阶段1", TimeUtil.toDate1("2024-04-11 17:10:00"), 1);
//		EvalTimeLineRsp lineRsp2 = new EvalTimeLineRsp("载荷2", TimeUtil.toDate1("2024-04-11 17:20:00"), 2);
//		List<EvalTimeLineRsp> lineRsps = new ArrayList<>();
//		lineRsps.add(lineRsp1);
//		lineRsps.add(lineRsp2);
//		EvalAllTimeLineRsp evalAllTimeLineRsp = new EvalAllTimeLineRsp();
//		evalAllTimeLineRsp.setStartTime(TimeUtil.toDate1("2024-04-11 17:00:00"));
//		evalAllTimeLineRsp.setEndTime(TimeUtil.toDate1("2024-04-11 17:30:00"));
//		evalAllTimeLineRsp.setData(lineRsps);
		return new BaseRsp<>(evalInstanceStepService.getTimeLine(instanceId));
	}

	@ApiOperation("任务情况")
	@GetMapping("/task")
	public BaseRsp<EvalTaskStatisticsRes> getTaskStatistics(@ApiParam(name = "instance_id", value = "实例id",
			required = true) @RequestParam("instance_id") String instanceId) {
		return new BaseRsp<>(evalInstanceStepService.getTaskStatistics(instanceId));
	}
}
