package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "态势回放")
@RestController
@Slf4j
@RequestMapping("/situation/playback")
public class SituationPlaybackController {

	@Autowired
	private GuideHistoryService guideHistoryService;

	@ApiOperation("刷新")
	@PostMapping("/refresh")
	public BaseRsp<GuideHistoryRsp> refresh(@RequestBody @Validated ScreenGuideHistoryReq screenGuideHistoryReq) {
		return new BaseRsp(guideHistoryService.getLocalRecord(screenGuideHistoryReq));
	}


}
