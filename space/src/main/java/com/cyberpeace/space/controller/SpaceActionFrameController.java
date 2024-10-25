package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFramePageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "知识库-防御行动框架-行动框架管理")
@RestController
@RequestMapping("/space/action_frame/action_frame")
@RequiresAuthentication
public class SpaceActionFrameController extends SpaceActionFrameBaseController<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq> {


    @ApiOperation("获取框架")
    @GetMapping("/frame")
    public BaseRsp getFrame(@ApiParam(value = "resource_id", name = "resource_id") String resource_id){


        return new BaseRsp();
    }

}
