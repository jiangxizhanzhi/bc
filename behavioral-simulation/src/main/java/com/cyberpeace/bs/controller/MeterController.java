package com.cyberpeace.bs.controller;

import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;
import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "行为模拟-行为设备管理")
@RestController
@RequestMapping("/bs/target")
// @RequiresAuthentication
public class MeterController {

	@Autowired
	MeterService meterService;

	@ApiOperation("启动单个任务")
	@PostMapping("/start_task")
	public BaseRsp startSingleTask(@RequestBody @Validated SingleBehaviorReq req){
		meterService.startSingleBehavior(req);
		return new BaseRsp();
	}

	@ApiOperation("停止单个行为")
	@DeleteMapping("/stop_task")
	public BaseRsp stopSingleTask(@RequestBody @Validated SingleBehaviorReq req){
		meterService.stopSingleBehavior(req);
		return new BaseRsp();
	}

}
