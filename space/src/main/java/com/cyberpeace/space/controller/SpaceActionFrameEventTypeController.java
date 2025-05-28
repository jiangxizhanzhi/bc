package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.service.SpaceActionFrameEventTypeService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameEventTypeBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动框架事件类型Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "防御行动框架-威胁事件类型")
@RestController
@RequestMapping("/space/action_frame/event_type")
@Slf4j
public class SpaceActionFrameEventTypeController extends SpaceControllerBase<SpaceActionFrameEventTypeService, SpaceActionFrameEventType, SpaceActionFrameEventTypeBo, SpacePageReqBase> {
}
    