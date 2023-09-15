package com.cyberpeace.bs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.bs.service.DeviceService;
import com.cyberpeace.bs.service.bo.device.DeviceAddReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 实体设备相关接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "实体设备相关")
@RestController
@RequestMapping("/bs/actual_device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;


    /**
     * 分配实体设备
     * 根据拓扑启动所需的实体设备类型和数量，返回分配的实体设备信息
     *
     * @param req 请求体
     * @return {@code BaseRsp<DeviceDistributionRes>}
     */
    @ApiOperation("分配实体设备")
    @PostMapping("/distribute")
    public BaseRsp<DeviceDistributionRes> distributeActualDevice(@RequestBody @Validated DeviceDistributionReq req) {
        DeviceDistributionRes res = deviceService.distribute(req);
        return new BaseRsp<>(res);
    }

    /**
     * 添加设备
     * 在系统中注册实体设备信息
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("添加实体设备")
    @PostMapping("/new")
    public BaseRsp add(@RequestBody @Validated DeviceAddReq req) {
        req.validate();
        deviceService.addDevice(req);
        return new BaseRsp();
    }
}
