
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceIocThreatService;
import com.cyberpeace.space.service.context.rule.SpaceIocThreatBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间国际 奥委会威胁Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-IOC威胁情报库")
@RestController
@RequestMapping("/space/rule/space_ioc_threat")
@RequiresAuthentication
@Slf4j
public class SpaceIocThreatController extends SpaceControllerBase<SpaceIocThreatService, SpaceIocThreat, SpaceIocThreatBo, SpacePageReqBase> {
}