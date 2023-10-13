package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryTimeLineReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.TimeLineRsp;
import com.cyberpeace.cr.crbase.service.GuideHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
		//前端6秒调一次接口
		screenGuideHistoryReq.setEndTime(TimeUtil.format1(TimeUtil.addSeconds(TimeUtil.toDate1(screenGuideHistoryReq.getStartTime()), screenGuideHistoryReq.getRate() * 6)));
		return new BaseRsp<>(guideHistoryService.getRecord(screenGuideHistoryReq));
	}

	@ApiOperation("单个执行")
	@PostMapping("/execute")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<Boolean> execute(@RequestBody @Validated ExecGuideHisReq execGuideHisReq) {
		return new BaseRsp<>(guideHistoryService.execute(execGuideHisReq));
	}

	@ApiOperation("态势复盘时间轴")
	@PostMapping("/playback/event/time")
	@RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<TimeLineRsp> getTimeLine(@RequestBody @Validated ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq) {
		TimeLineRsp timeLineRsp = new TimeLineRsp();
		if (CollectionUtils.isEmpty(screenGuideHistoryTimeLineReq.getEventSubTypeList())) {
			throw new BusinessException(CrBusinessErrorCode.NO_EVENT_TYPE_SELECTED);
		}
		String startTime = guideHistoryService.getStartTime(screenGuideHistoryTimeLineReq);
		if (startTime == null) {
			throw new BusinessException(CrBusinessErrorCode.ACTION_TYPE_NOT_SELECTED);
		}
		timeLineRsp.setStartTime(startTime);
		String endTime = guideHistoryService.getEndTime(screenGuideHistoryTimeLineReq);
		if (endTime == null) {
			throw new BusinessException(CrBusinessErrorCode.ACTION_TYPE_NOT_SELECTED);
		}
		timeLineRsp.setEndTime(endTime);
		List<String> eventTimeList = guideHistoryService.getEventTimeList(screenGuideHistoryTimeLineReq);
		eventTimeList = eventTimeList.stream().filter(time -> TimeUtil.toDate1(time).after(TimeUtil.toDate1(timeLineRsp.getStartTime()))
				&& TimeUtil.toDate1(time).before(TimeUtil.toDate1(timeLineRsp.getEndTime()))).collect(Collectors.toList());
		timeLineRsp.setEventTimeList(eventTimeList);
		return new BaseRsp<>(timeLineRsp);
	}

}
