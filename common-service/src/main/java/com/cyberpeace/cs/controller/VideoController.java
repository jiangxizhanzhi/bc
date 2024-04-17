package com.cyberpeace.cs.controller;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "视频进度相关api")
@Slf4j
@RestController
@RequestMapping("/cs/video")
@RequiresAuthentication
public class VideoController {

	@Autowired
	private HandoutVideoService handoutVideoService;

	@ApiOperation("上报视频检查点进度")
	@PostMapping("/check/report")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp postVideoProgress(@RequestBody @Valid VideoProgressReportReq req) {
		handoutVideoService.updateProgress(req);
		return new BaseRsp();
	}
}
