package com.cyberpeace.bs.controller;


import com.cyberpeace.bs.service.DeviceService;
import com.cyberpeace.bs.service.bo.device.DeviceAddReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;
import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "实体设备相关")
@RestController
@RequestMapping("/bs/actual_device")
// @RequiresAuthentication
public class DeviceController {

    @Autowired
    DeviceService deviceService;


    @ApiOperation("分配实体设备")
    @PostMapping("/distribute")
    public BaseRsp<DeviceDistributionRes> distributeActualDevice(@RequestBody @Validated DeviceDistributionReq req){
        DeviceDistributionRes res = deviceService.distribute(req);
        return new BaseRsp<>(res);
    }

    @ApiOperation("添加实体设备")
    @PostMapping("/new")
    public BaseRsp add(@RequestBody @Validated DeviceAddReq req){
        req.validate();
        deviceService.addDevice(req);
        return new BaseRsp();
    }
}
