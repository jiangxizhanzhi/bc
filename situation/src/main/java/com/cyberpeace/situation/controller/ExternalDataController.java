package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.integratation.service.impl.SituationEnvService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.res.EventListRsp;
import com.cyberpeace.integratation.service.RemoteOperationService;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.controller.req.EventMsgReq;
import com.cyberpeace.situation.controller.req.TaskEventMsgReq;
import com.cyberpeace.situation.controller.res.EvaluateUrlRes;
import com.cyberpeace.situation.controller.res.EventTypeRes;
import com.cyberpeace.situation.service.ThirdPartyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "外部数据")
@RestController
@RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataController {

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private RemoteOperationService remoteOperationService;

	@Autowired
	private SituationEnvService situationEnvService;

	@ApiOperation("态势-获取特定事件类型列表")
	@GetMapping("/event/list/")
	public BaseRsp<List<EventListRsp>> getEvents() {
		return new BaseRsp<>(thirdPartyService.getTypicalEvents());
	}

	@ApiOperation("态势-获取任务事件列表")
	@PostMapping("/event/task/")
	public BaseRsp<List<TaskEventContext>>
			getTaskEventList(@RequestBody @Validated TaskEventMsgReq taskEventReq) {
		return new BaseRsp<>(remoteSituationService.getTaskEventList(taskEventReq, null));
	}

	@ApiOperation("态势-获取节点状态列表")
	@PostMapping("/event/node/")
	public BaseRsp<List<NodeEventContext>>
			getNodeEventList(@RequestBody @Validated EventMsgReq eventReq) {

		return new BaseRsp<>(remoteSituationService.getNodeEventList(eventReq, null));
	}

	@ApiOperation("态势-获取攻防事件类型列表")
	@GetMapping("/event_type/list/")
	public BaseRsp<List<EventTypeRes>> getEventTypes() {

		return new BaseRsp<>(thirdPartyService.getEventTypes());
	}

	@ApiOperation("态势-获取攻防事件列表")
	@PostMapping("/event/ad/")
	public BaseRsp<List<AdEventContext>>
			getAdEventList(@RequestBody @Validated EventMsgReq eventReq) {

		return new BaseRsp<>(remoteSituationService.getAdEventList(eventReq, null));
	}

	@ApiOperation("运维-获取站点列表")
	@GetMapping("/bc/list/")
	public BaseRsp<List<BcContext>>
			getBcList(@ApiParam(value = "站点类型", name = "type") String type) {
		List<BcContext> res = remoteOperationService.getBcList(type);
		return new BaseRsp<>(res);
	}

	@ApiOperation("运维-获取模型列表")
	@GetMapping("/model/list/")
	public BaseRsp<Map<String, ModelContext>> getModelList() {
		Map<String, ModelContext> res = remoteOperationService.getModelListMap();
		return new BaseRsp<>(res);
	}

	@ApiOperation("运维-获取模型类型列表")
	@GetMapping("/model/type/list/")
	public BaseRsp<Map<String, ModelTypeContext>> getModelTypeList() {
		Map<String, ModelTypeContext> res = remoteOperationService.getModelTypeListMap();
		return new BaseRsp<>(res);
	}

	@ApiOperation("评估-获取评估页面链接")
	@GetMapping("/evaluate/url/")
	public BaseRsp<EvaluateUrlRes> getEvaluateUrl(
			@ApiParam(name = "subject_id", value = "科目ID", required = true) @RequestParam(
					value = "subject_id", required = true) String subjectId,
			@ApiParam(name = "role", value = "角色", required = true) @RequestParam(value = "role",
					required = true) String role) {
		String runId = remoteSituationService.getRunId(subjectId);
		EvaluateUrlRes evaluateUrlRes = new EvaluateUrlRes(runId, role);
		Integer type = EnumUtils.getEnumCodeByDesc(role, EvaluateUrlRes.Role.class);

		String url = situationEnvService.getPgPageHost()
				+ ConfigUtil.getContextProperty("pg.run.page") + "&runId=" + runId + "&type="
				+ type;

		evaluateUrlRes.setUrl(url);

		return new BaseRsp<>(evaluateUrlRes);
	}

}
