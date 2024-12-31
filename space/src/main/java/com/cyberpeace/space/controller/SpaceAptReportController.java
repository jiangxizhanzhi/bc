package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptReportService;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-理论知识库-APT报告")
@RestController
@RequestMapping("/space/theory/space_apt_report")
@RequiresAuthentication
@Slf4j
public class SpaceAptReportController extends SpaceControllerBase<SpaceAptReportService, SpaceAptReport, SpaceAptReportBo> {

}
