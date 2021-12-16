package com.cyberpeace.cr.redvsblue.controller.director;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;

@Api(tags = "红蓝对抗模板后台")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/director/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateDirectorController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private LocalSceneService localSceneService;

}
