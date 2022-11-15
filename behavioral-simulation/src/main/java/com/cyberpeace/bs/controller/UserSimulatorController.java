package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.IdReq;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorAddReq;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorBo;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "前台业务-网络流量发生")
@RestController
@RequestMapping("/bs/simulator")
// @RequiresAuthentication
public class UserSimulatorController {

	@Autowired
	UserSimulatorService userSimulatorService;

	@ApiOperation("前台-用户流量发生列表")
	@GetMapping("/list")
	public BaseRsp<List<UserSimulatorBo>> listTraffic() {
		List<UserSimulatorBo> ret = userSimulatorService.listSimulator();
		return new BaseRsp<>(ret);
	}

	@ApiOperation("创建用户流量发生")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Validated UserSimulatorAddReq addReq) {
		userSimulatorService.create(addReq);
		return new BaseRsp();
	}

	@ApiOperation("批量删除用户流量发生")
	@DeleteMapping("/")
	public BaseRsp delete(@RequestBody @Validated IdListReq deleteReq) {
		userSimulatorService.batchDelete(deleteReq.getIds());
		return new BaseRsp();
	}

	@ApiOperation("启动用户流量")
	@PostMapping("/start")
	public BaseRsp start(@RequestBody @Validated IdReq idReq) {
		userSimulatorService.startSimulator(idReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("停止用户流量")
	@PostMapping("/stop")
	public BaseRsp stop(@RequestBody @Validated IdReq idReq) {
		userSimulatorService.stopSimulator(idReq.getResourceId());
		return new BaseRsp();
	}
}
