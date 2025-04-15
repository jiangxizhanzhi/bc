package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.dao.entity.SpaceActionFrameTargetType;
import com.cyberpeace.space.service.SpaceActionFrameTargetTypeService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "防御行动框架-目标类型")
@RestController
@RequestMapping("/space/action_frame/target_type")
@RequiresAuthentication
@Slf4j
public class SpaceActionFrameTargetTypeController extends SpaceControllerBase<SpaceActionFrameTargetTypeService, SpaceActionFrameTargetType, SpaceActionFrameTargetTypeBo, SpacePageReqBase> {
}
    