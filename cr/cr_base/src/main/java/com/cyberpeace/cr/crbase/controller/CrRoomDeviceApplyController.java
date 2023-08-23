package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.RoomDeviceApplyAllowReq;
import com.cyberpeace.cr.crbase.controller.req.RoomDeviceApplyListReq;
import com.cyberpeace.cr.crbase.controller.req.RoomDeviceApplyReq;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceApplyListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceOccupyRsp;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "训练用户申请使用实装设备")
@RestController
@Slf4j
@RequestMapping("/cr/room/device_apply")
public class CrRoomDeviceApplyController {
    @Autowired
    private CrRoomDeviceApplyService crRoomDeviceApplyService;

    @ApiOperation("实装分配-设备审批列表")
    @GetMapping("/admin/list")
    public BaseRsp<ListRsp<RoomDeviceApplyListRsp>> list(RoomDeviceApplyListReq requestParams) {
        requestParams.setSort("-apply_time");
        return new BaseRsp<>(crRoomDeviceApplyService.list(requestParams.toPageParam(), requestParams.getDeviceId()));
    }

    @ApiOperation("实装分配-当前占用")
    @GetMapping("/admin/occupy")
    public BaseRsp<RoomDeviceOccupyRsp> occupy(
            @ApiParam(value = "设备id", required = true) @RequestParam("device_id") String deviceId) {
        return new BaseRsp<>(crRoomDeviceApplyService.occupy(deviceId));
    }

    @ApiOperation("访问控制")
    @PostMapping("/admin/allow")
    public BaseRsp<Boolean> allow(@RequestBody @Validated RoomDeviceApplyAllowReq roomDeviceApplyAllowReq) {
        return new BaseRsp<>(crRoomDeviceApplyService.allow(roomDeviceApplyAllowReq.getApplyId()));
    }

    @ApiOperation("实装分配-设备审批拒绝")
    @PostMapping("/admin/refuse")
    public BaseRsp<Boolean> refuse(@RequestBody @Validated RoomDeviceApplyAllowReq roomDeviceApplyAllowReq) {
        return new BaseRsp<>(crRoomDeviceApplyService.refuse(roomDeviceApplyAllowReq.getApplyId()));
    }

    @ApiOperation("实装分配-设备占用回收")
    @PostMapping("/admin/recycle")
    public BaseRsp<Boolean> recycle(@RequestBody @Validated RoomDeviceApplyReq roomDeviceApplyReq) {
        return new BaseRsp<>(crRoomDeviceApplyService.recycle(roomDeviceApplyReq.getRoomId(), roomDeviceApplyReq.getDeviceId()));
    }
}
