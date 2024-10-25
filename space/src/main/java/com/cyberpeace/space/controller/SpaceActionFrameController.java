package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameLevel;
import com.cyberpeace.space.service.context.frame.SpaceActionFramePageReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "知识库-防御行动框架-行动框架管理")
@RestController
@RequestMapping("/space/action_frame/action_frame")
@RequiresAuthentication
public class SpaceActionFrameController extends SpaceActionFrameBaseController<SpaceActionFrameService, SpaceActionFrame, SpaceActionFrameBo, SpaceActionFramePageReq> {

	@Autowired
	private SpaceActionFrameService service;

    @ApiOperation("获取框架")
    @GetMapping("/frame")
	public BaseRsp<SpaceActionFrameLevel>
			getFrame(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		SpaceActionFrameLevel level = service.getFrameLevels(resource_id);
		return new BaseRsp<>(level);
    }

}
