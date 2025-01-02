
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.service.SpaceFingerPrintService;
import com.cyberpeace.space.service.context.rule.SpaceFingerPrintBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-指纹库")
@RestController
@RequestMapping("/space/rule/space_finger_print")
@RequiresAuthentication
@Slf4j
public class SpaceFingerPrintController extends SpaceControllerBase<SpaceFingerPrintService, SpaceFingerPrint, SpaceFingerPrintBo, SpacePageReqBase>
    
{}