package com.cyberpeace.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.service.SystemUseStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Api(tags = "系统信息服务接口")
@RestController
@RequestMapping("/system_property")
@RequiresAuthentication
@Slf4j
public class SystemPropertyController {

	@Autowired
	private SystemPropertyService systemPropertyService;

	@ApiOperation("靶场应用运行图")
	@GetMapping("/list_by_type")
	public BaseRsp<List<SystemProperty>>
			appRunningInfo(@RequestParam(value = "type", required = false) Integer type) {
		QueryWrapper<SystemProperty> queryWrapper = new QueryWrapper<>();
		if (type != null) {
			queryWrapper.lambda().eq(SystemProperty::getType, type);
		}
		return new BaseRsp<>(systemPropertyService.list(queryWrapper));
	}

}
