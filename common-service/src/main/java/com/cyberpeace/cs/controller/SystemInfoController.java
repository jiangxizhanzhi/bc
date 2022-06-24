package com.cyberpeace.cs.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.service.SystemUseStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "系统信息服务接口")
@RestController
@RequestMapping("/system_info")
@RequiresAuthentication
@Slf4j
public class SystemInfoController {

	@Autowired
	private SystemUseStatusService systemUseStatusService;

	@ApiOperation("靶场应用运行图")
	@GetMapping("/app_running_info")
	public BaseRsp<SystemUseStatusRes> appRunningInfo() {
		SystemUseStatusRes res = systemUseStatusService.showAll();
		systemUseStatusService.delete();
		return new BaseRsp<>(res);
	}

}
