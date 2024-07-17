package com.cyberpeace.cs.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.res.ExpirationCheckRes;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "获取系统变量")
@RestController
@RequestMapping("/system_property")
@Slf4j
public class SystemPropertyController {

    @Autowired
    private SystemPropertyService systemPropertyService;

    @ApiOperation("全量获取")
    @GetMapping("/list")
    public BaseRsp<List<SystemProperty>> appRunningInfo() {
        List<SystemProperty> list = systemPropertyService.list();
        return new BaseRsp<>(list);
    }

	@ApiOperation("获取到期日期")
	@GetMapping("/exp_date")
	public BaseRsp<ExpirationCheckRes> getExpDate() {
		Date date = systemPropertyService.getExpirationDate();
		ExpirationCheckRes res = new ExpirationCheckRes();
		res.setExpirationDate(date);
		res.setExpire(new Date().after(date));
		return new BaseRsp<>(res);
	}
}
