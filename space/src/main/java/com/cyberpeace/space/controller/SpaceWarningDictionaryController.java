
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceWarningDictionary;
import com.cyberpeace.space.service.SpaceWarningDictionaryService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-攻击告警字典规则")
@RestController
@RequestMapping("/space/rule/space_warning_dictionary")
@RequiresAuthentication
@Slf4j
public class SpaceWarningDictionaryController extends SpaceControllerBase<SpaceWarningDictionaryService, SpaceWarningDictionary, SpaceWarningDictionaryBo>
    
{}