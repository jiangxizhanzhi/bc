package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.PlatformReq;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.site.CenterContext;
import com.cyberpeace.situation.service.context.site.RangeContext;
import com.cyberpeace.situation.service.context.site.StationContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "外部数据-地理信息")
@RestController
@RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataGisController {

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@ApiOperation("运维-获取LG中心列表")
	@GetMapping("/center/list/")
	public BaseRsp<List<CenterContext>> getCenters() {
		return new BaseRsp<>(thirdPartyService.getCenters());
	}

	@ApiOperation("运维-获取分靶场列表")
	@GetMapping("/range/list/")
	public BaseRsp<List<RangeContext>> getSubRanges(@ApiParam(name = "type", value = "靶场类型",
			required = false) @RequestParam(value = "type", required = false) String type) {

		return new BaseRsp<>(thirdPartyService.getRanges(type));
	}

	@ApiOperation("运维-获取场站列表")
	@GetMapping("/station/list/")
	public BaseRsp<List<StationContext>>
			getStations(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {

		return new BaseRsp<>(thirdPartyService.getStations(subRangeId));
	}

	@ApiOperation("态势-获取分靶场详情")
	@GetMapping("/range/")
	public BaseRsp<SubRangeInfoContext>
			getSubRangeInfo(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = remoteSituationService
				.getSubRangeInfo(subRangeId);
		subRangeInfoContext.setTitle(subRangeInfoContext.getSubRangeName() + "综合态势");

		return new BaseRsp<>(subRangeInfoContext);
	}

	@ApiOperation("态势-获取LG中心详情")
	@GetMapping("/center/")
	public BaseRsp<CenterInfoContext> getCenterInfo(@ApiParam(name = "center_id", value = "LG中心ID",
			required = true) @RequestParam(value = "center_id") String centerId) {
		CenterInfoContext centerInfoContext = remoteSituationService.getCenterInfo(centerId);

		return new BaseRsp<>(centerInfoContext);
	}

	@ApiOperation("想定-获取平台列表")
	@GetMapping("/platform/list/")
	public BaseRsp<List<PlatformContext>> getPlatforms(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId,
			@ApiParam(name = "scheme_id", value = "可视化方案ID", required = false) @RequestParam(
					value = "scheme_id", required = false) String schemeId) {
		List<PlatformContext> platformList = remoteSituationService.getPlatformList(taskId,
				subjectId, subRangeId, schemeId);

		return new BaseRsp<>(platformList);
	}

	@ApiOperation("想定-获取平台连线列表")
	@GetMapping("/platform/link/list/")
	public BaseRsp<List<PlatformLinkContext>> getPlatformLinks(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId,
			@ApiParam(name = "scheme_id", value = "可视化方案ID", required = false) @RequestParam(
					value = "scheme_id", required = false) String schemeId) {
		List<PlatformLinkContext> platformLinkList = remoteSituationService
				.getPlatformLinkList(taskId, subjectId, subRangeId, schemeId);
		return new BaseRsp<>(platformLinkList);
	}

	@ApiOperation("态势-获取平台网络信息")
	@PostMapping("/network/")
	public BaseRsp<NetworkInfoContext>
			getNetworkInfo(@RequestBody @Validated PlatformReq platformReq) {
		return new BaseRsp<>(remoteSituationService.getNetworkInfo(platformReq));
	}

}
