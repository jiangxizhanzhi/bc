package com.cyberpeace.bs.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.CommandBo;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;
import com.cyberpeace.bs.service.bo.meter.SimulatorTimer;
import com.cyberpeace.bs.service.bo.simulator.SimulatorAddBo;
import com.cyberpeace.bs.service.bo.simulator.SimulatorModifyBo;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "行为模拟-辅助测试")
@RestController
@RequestMapping("/bs/meter")
// @RequiresAuthentication
public class MeterController {

	@Autowired
	MeterService meterService;

	@Autowired
	UserSimulatorService userSimulatorService;

	@ApiOperation("启动单个任务")
	@PostMapping("/start_task")
	public BaseRsp startSingleTask(@RequestBody @Validated SingleBehaviorReq req) {
		meterService.startSingleBehavior(req);
		return new BaseRsp();
	}

	@ApiOperation("停止单个行为")
	@DeleteMapping("/stop_task")
	public BaseRsp stopSingleTask(@RequestBody @Validated SingleBehaviorReq req) {
		meterService.stopSingleBehavior(req);
		return new BaseRsp();
	}

	@ApiOperation("注入行为步骤数据")
	@PostMapping("/stepData")
	public BaseRsp importStepData(@RequestBody Map<String, Integer> req) {
		BsUtil.stepMap.putAll(req);
		return new BaseRsp();
	}

	@ApiOperation("标记行为仪表")
	@PostMapping("/note")
	public BaseRsp noteSimulators(@RequestBody Map<String, LocalTime> req) {
		BsUtil.markedId.putAll(req);
		return new BaseRsp();
	}

	@ApiOperation("查看定时任务")
	@GetMapping("/task")
	public BaseRsp<List<SimulatorTimer>> getTimers() {
		List<SimulatorTimer> res = userSimulatorService.showTimers();
		return new BaseRsp<>(res);
	}

	@ApiOperation("test command 1")
	@PostMapping("/test/create")
	public BaseRsp testCommand1(@RequestBody @Validated CommandBo<SimulatorAddBo> commandBo) {
		SimulatorAddBo addBo = commandBo.getCommandBody();
		return new BaseRsp(addBo);
	}

	@ApiOperation("test command 2")
	@PostMapping("/test/modify")
	public BaseRsp testCommand2(@RequestBody @Validated CommandBo<SimulatorModifyBo> commandBo) {
		SimulatorModifyBo addBo = commandBo.getCommandBody();
		return new BaseRsp(addBo);
	}

	@ApiOperation("test command 1")
	@PostMapping("/test/delete")
	public BaseRsp testCommand3(@RequestBody @Validated CommandBo<IdListReq> commandBo) {
		IdListReq addBo = commandBo.getCommandBody();
		return new BaseRsp(addBo);
	}

}
