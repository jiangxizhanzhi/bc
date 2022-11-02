package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.bs.controller.req.IdReq;
import com.cyberpeace.bs.controller.req.UserTrafficAddReq;
import com.cyberpeace.bs.service.UserTrafficService;
import com.cyberpeace.bs.service.bo.traffic.UserTrafficBo;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "前台业务-网络流量发生")
@RestController
@RequestMapping("/bs/traffic")
// @RequiresAuthentication
public class UserTrafficController {

	@Autowired
	UserTrafficService userTrafficService;

	@ApiOperation("前台-用户流量发生列表")
	@GetMapping("/list")
	public BaseRsp<List<UserTrafficBo>> listTraffic() {
		List<UserTrafficBo> ret = userTrafficService.listTraffic();
		return new BaseRsp<>(ret);
	}

	@ApiOperation("创建用户流量发生")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Validated UserTrafficAddReq addReq) {
		userTrafficService.create(addReq.getUserBehaviorId());
		return new BaseRsp();
	}

	@ApiOperation("批量删除用户流量发生")
	@DeleteMapping("/")
	public BaseRsp delete(@RequestBody @Validated DeleteReq deleteReq) {
		userTrafficService.batchDelete(deleteReq.getIds());
		return new BaseRsp();
	}

	@ApiOperation("启动用户流量")
	@PostMapping("/start")
	public BaseRsp start(@RequestBody @Validated IdReq idReq) {
		userTrafficService.startTraffic(idReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("停止用户流量")
	@PostMapping("/stop")
	public BaseRsp stop(@RequestBody @Validated IdReq idReq) {
		userTrafficService.stopTraffic(idReq.getResourceId());
		return new BaseRsp();
	}
}
