package com.cyberpeace.situation.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.situation.CenterInfoContext;
import com.cyberpeace.integratation.model.situation.NetworkInfoContext;
import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.model.situation.SubRangeInfoContext;
import com.cyberpeace.integratation.model.situation.req.NodeListApiReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.TaskType;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.dao.entity.Center;
import com.cyberpeace.situation.dao.entity.Station;
import com.cyberpeace.situation.dao.entity.SubRange;
import com.cyberpeace.situation.service.ThirdPartyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "三方数据")
@RestController
@RequestMapping("/third/")
public class ThirdPartyController {

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@ApiOperation("获取攻防事件类型列表")
	@GetMapping("/event_type/list/")
	public BaseRsp<List<EventTypeRes>> getEventTypes() {

		return new BaseRsp<>(thirdPartyService.getEventTypes());
	}

	@ApiOperation("获取LG中心列表")
	@GetMapping("/center/list/")
	public BaseRsp<List<Center>> getCenters() {

		return new BaseRsp<>(thirdPartyService.getCenters());
	}

	@ApiOperation("获取分靶场列表")
	@GetMapping("/range/list/")
	public BaseRsp<List<SubRange>> getSubRanges() {

		return new BaseRsp<>(thirdPartyService.getSubRanges());
	}

	@ApiOperation("获取场站列表")
	@GetMapping("/station/list/")
	public BaseRsp<List<Station>> getStations(@ApiParam(name = "sub_range_id", value = "分靶场ID",
			required = true) @RequestParam(value = "sub_range_id") String subRangeId) {

		return new BaseRsp<>(thirdPartyService.getStations(subRangeId));
	}

	@ApiOperation("获取分靶场详情")
	@GetMapping("/range/")
	public BaseRsp<SubRangeInfoContext>
			getSubRangeInfo(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = remoteSituationService
				.getSubRangeInfo(subRangeId);
		subRangeInfoContext.setTitle("XXX分靶场综合态势");
		return new BaseRsp<>(subRangeInfoContext);
	}

	@ApiOperation("获取LG中心详情")
	@GetMapping("/center/")
	public BaseRsp<CenterInfoContext> getCenterInfo(@ApiParam(name = "center_id", value = "LG中心ID",
			required = true) @RequestParam(value = "center_id") String centerId) {
		CenterInfoContext centerInfoContext = remoteSituationService.getCenterInfo(centerId);
		return new BaseRsp<>(centerInfoContext);
	}

	@ApiOperation("获取平台列表")
	@GetMapping("/platform/list/")
	public BaseRsp<List<PlatformContext>> getPlatforms(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId) {
		List<PlatformContext> platformList = remoteSituationService.getPlatformList(taskId,
				subjectId, subRangeId);
		return new BaseRsp<>(platformList);
	}

	@ApiOperation("获取任务类型字典")
	@GetMapping("/task_type/list/")
	public BaseRsp<List<HashMap<String, String>>> getTaskType() {
		return new BaseRsp<>(TaskType.getTaskTypeMap());
	}

	@ApiOperation("获取网络信息")
	@PostMapping("/network/")
	public BaseRsp<NetworkInfoContext>
			getTaskType(@RequestBody @Validated NodeListApiReq nodeListApiReq) {
		return new BaseRsp<>(remoteSituationService.getNetworkInfo(nodeListApiReq));
	}
}
