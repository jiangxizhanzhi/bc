package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceActionDefense;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionDefenseService;
import com.cyberpeace.space.service.context.frame.SpaceActionDefenseBo;
import com.cyberpeace.space.service.context.frame.SpaceActionDefensePageReq;

import io.swagger.annotations.Api;

@Api(tags = "知识库-防御行动框架-防御活动管理")
@RestController
@RequestMapping("/space/action_frame/action_defense")
@RequiresAuthentication
public class SpaceActionDefenseController extends SpaceActionFrameBaseController<SpaceActionDefenseService, SpaceActionDefense, SpaceActionDefenseBo, SpaceActionDefensePageReq> {

}
