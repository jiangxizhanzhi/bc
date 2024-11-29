package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationServerRsp;
import com.cyberpeace.cr.evaluation.service.EvalSituationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "靶场测评-城市沙盘")
@RestController
@RequestMapping("/cr/eval/situation")
@Slf4j
public class EvalSituationController {

	@Autowired
	private EvalSituationService evalSituationService;

	@ApiOperation("获取靶场测评实例列表")
	@GetMapping("/get_instances")
	public List<EvalSituationListRsp> getInstances() {
		return evalSituationService.getAllEvalSituation();
	}

	@ApiOperation("获取靶场测评实例节点数据")
	@GetMapping("/get_servers")
	public List<EvalSituationServerRsp> getServers(
			@ApiParam(name = "resource_id", value = "测评任务编号") @RequestParam(value = "resource_id") String resourceId) {
		return evalSituationService.getEvalServerByResourceId(resourceId);
	}

	@ApiOperation("推送流量日志数据")
	@GetMapping("/push_flow")
	public BaseRsp<Boolean> pushFlow(@ApiParam(name = "resource_id", value = "测评任务编号") @RequestParam(value = "resource_id") String resourceId) {
		evalSituationService.pushFlow(resourceId);
		return new BaseRsp<>(Boolean.TRUE);
	}

	@ApiOperation("推送载荷执行结果数据")
	@ApiParam(name = "resource_id", value = "测评任务编号")
	@PostMapping("/push_load_result")
	public BaseRsp<Boolean> pushLoadResult( @RequestParam(value = "resource_id") String resourceId,
											@RequestParam("file") MultipartFile file) {
		evalSituationService.pushLoadResult(resourceId,file);
		return new BaseRsp<>(Boolean.TRUE);
	}
}
