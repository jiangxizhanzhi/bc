package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.service.context.EventTypeContext;
import com.cyberpeace.situation.service.context.PlatformContext;
import com.cyberpeace.situation.service.context.StationContext;
import com.cyberpeace.situation.service.context.SubRangeContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "三方数据")
@RestController
@RequestMapping("/third/")
public class ThirdPartyController {

	@ApiOperation("获取攻防事件类型列表")
	@GetMapping("/event_type/list/")
	public BaseRsp<List<EventTypeRes>> getEventTypes() {

		List<EventTypeRes> eventTypeList = Lists.newArrayList();

		IntStream.range(0, 3).forEachOrdered(i -> {
			EventTypeRes eventTypeRes = new EventTypeRes(String.valueOf(i), "事件类型" + i);
			eventTypeRes.setSubTypes(
					Lists.newArrayList(new EventTypeContext(String.valueOf(i * 4), "子类型" + i)));
			eventTypeList.add(eventTypeRes);
		});

		return new BaseRsp<>(eventTypeList);

	}

	@ApiOperation("获取分靶场列表")
	@GetMapping("/range/list/")
	public BaseRsp<List<SubRangeContext>> getSubRanges() {

		List<SubRangeContext> subRangeList = Lists.newArrayList();
		subRangeList.add(new SubRangeContext("zhxfbc", "综合性分靶场"));
		subRangeList.add(new SubRangeContext("wlkjjjfyfbc", "网络空间JJFY分靶场"));
		return new BaseRsp<>(subRangeList);
	}

	@ApiOperation("获取场站列表")
	@GetMapping("/station/list/")
	public BaseRsp<List<StationContext>>
			getStations(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {
		List<StationContext> stationList = Lists.newArrayList();
		stationList.add(new StationContext("zhzcz", "ZH主场站"));
		return new BaseRsp<>(stationList);
	}

	@ApiOperation("获取平台列表")
	@GetMapping("/platform/list/")
	public BaseRsp<List<PlatformContext>> getPlatforms(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId) {
		List<PlatformContext> platformList = Lists.newArrayList();
		platformList.add(new PlatformContext("zhs1", "指挥所1"));
		platformList.add(new PlatformContext("zhs2", "指挥所2"));
		return new BaseRsp<>(platformList);
	}

}
