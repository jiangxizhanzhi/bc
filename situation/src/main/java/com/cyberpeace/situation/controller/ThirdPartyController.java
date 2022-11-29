package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.service.context.EventTypeContext;
import com.cyberpeace.situation.service.context.SubRangeContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

}
