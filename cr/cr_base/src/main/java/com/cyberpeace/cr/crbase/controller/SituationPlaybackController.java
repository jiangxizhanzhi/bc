package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "态势回放")
@RestController
@Slf4j
@RequestMapping("/situation/playback")
public class SituationPlaybackController {

	@Autowired
	private GuideHistoryService guideHistoryService;

	@ApiOperation("刷新")
	@PostMapping("/refresh")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<List<GuideHistoryRsp>> refresh(@RequestBody @Validated ScreenGuideHistoryReq screenGuideHistoryReq) {
		screenGuideHistoryReq.setEndTime(TimeUtil.format1(TimeUtil.addSeconds(TimeUtil.toDate1(screenGuideHistoryReq.getStartTime()), screenGuideHistoryReq.getRate() * 5)));
		return new BaseRsp<>(guideHistoryService.getRecord(screenGuideHistoryReq));
	}

	@ApiOperation("单个执行")
	@PostMapping("/execute")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<Boolean> execute(@RequestBody @Validated ExecGuideHisReq execGuideHisReq) {
		return new BaseRsp<>(guideHistoryService.execute(execGuideHisReq));
	}

}
