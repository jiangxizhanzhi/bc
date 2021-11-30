package com.cyberpeace.cr.singletrain.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomListBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "单兵训练")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/single_train/room")
public class SingleRoomController {

    @Autowired
    private SingleRoomService singleRoomService;

    @PostMapping
    @ApiOperation("创建")
    public BaseRsp<SingleRoomBo> create(@RequestParam("resource_id") String businessTrainId) {
        return new BaseRsp<>(singleRoomService.createRoom(businessTrainId));
    }

    @GetMapping
    @ApiOperation("详情")
    public BaseRsp<SingleRoomBo> get(@RequestParam("resource_id") String businessTemplateId) {
        return new BaseRsp<>(singleRoomService.getRoomInfo(businessTemplateId));
    }

    @DeleteMapping
    @ApiOperation("关闭")
    public BaseRsp<Boolean> delete(@RequestParam("resource_id") String businessTemplateId) {
        return new BaseRsp<>(singleRoomService.deleteRoom(businessTemplateId));
    }

    @GetMapping("/list")
    @ApiOperation("分页查询")
    public BaseRsp<ListRsp<SingleRoomListBo>> list(SingleTrainListReq queryParams) {
        // todo 节点数量, 日期, 日训练量
        singleRoomService.getSingleTrainPageList(queryParams);
        return new BaseRsp<>();
    }
}
