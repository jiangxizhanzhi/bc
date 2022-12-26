package com.cyberpeace.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.gateway.controller.req.ControlCommandReq;
import com.cyberpeace.gateway.controller.req.EmissionReq;
import com.cyberpeace.gateway.controller.req.PlanBindSituationReq;
import com.cyberpeace.gateway.controller.req.SituationSchemeBo;
import com.cyberpeace.gateway.controller.res.ControlRes;
import com.cyberpeace.gateway.controller.res.ScreenPathRes;
import com.cyberpeace.integratation.service.bo.situation.BooleanRes;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.TopoHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-态势相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation")
public class ExternalSituationController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@CrossOrigin("*")
	@ApiOperation("接收任务指挥命令")
	@PostMapping("control")
	public BaseRsp<ControlRes>
			controlCmd(@RequestBody @Validated ControlCommandReq controlCommandReq) {

		log.info(String.format("成功接收指令：%s", JsonUtil.toJsonString(controlCommandReq)));

		return new BaseRsp<>(new ControlRes("1", "0"));
	}

	@CrossOrigin("*")
	@ApiOperation("接收可视化方案的新建、编辑")
	@PostMapping("/scheme")
	public BaseRsp<ScreenPathRes>
			receiveScheme(@RequestBody @Validated SituationSchemeBo schemeBo) {
		log.info("成功接收可视化方案：{}", JsonUtil.toJsonString(schemeBo));
		ScreenPathRes res = new ScreenPathRes();
		res.setPath("/xx/yy");
		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("接收想定下发大屏指令")
	@PostMapping("/emission")
	public BaseRsp<BooleanRes> receiveEmission(@RequestBody @Validated EmissionReq req) {
		log.info("成功接收指令：{}", JsonUtil.toJsonString(req));
		BooleanRes res = new BooleanRes();
		res.setResult(true);
		return new BaseRsp<>(res);
	}
}
