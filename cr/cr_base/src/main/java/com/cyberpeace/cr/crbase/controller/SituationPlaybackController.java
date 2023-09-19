package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
	public BaseRsp<List<GuideHistoryRsp>> refresh(@RequestBody @Validated ScreenGuideHistoryReq screenGuideHistoryReq) {
		return new BaseRsp<>(guideHistoryService.getRecord(screenGuideHistoryReq));
	}

	@ApiOperation("获取时间轴")
	@GetMapping("/event/time")
	public BaseRsp<List<String>> getEventTime(
			@RequestParam(name = "room_id") @ApiParam(name = "room_id" ,value = "房间id") String roomId,
			@RequestParam(name = "business_type") @ApiParam(name = "business_type" ,value = "训练类型") Integer businessType
	) {
		return new BaseRsp<>(guideHistoryService.getEventTimeList(roomId, businessType));
	}


}
