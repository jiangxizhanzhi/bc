
package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.controller.req.SpaceAptFeaturePageReq;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptFeatureService;
import com.cyberpeace.space.service.context.rule.SpaceAptFeatureBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间容易特征Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-APT组织库")
@RestController
@RequestMapping("/space/rule/space_apt_feature")
@RequiresAuthentication
@Slf4j
public class SpaceAptFeatureController extends SpaceControllerBase<SpaceAptFeatureService, SpaceAptFeature, SpaceAptFeatureBo, SpaceAptFeaturePageReq> {
    
}