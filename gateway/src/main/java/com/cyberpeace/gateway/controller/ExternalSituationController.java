package com.cyberpeace.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.gateway.controller.req.ControlCommandReq;
import com.cyberpeace.gateway.controller.req.PlanBindSituationReq;
import com.cyberpeace.gateway.controller.res.ControlRes;
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
	@ApiOperation("获取场景拓扑")
	@GetMapping("topology")
	public BaseRsp<SituationTopo> topology(
			@ApiParam(name = "scene_id", value = "场景编号") @RequestParam(value = "scene_id",
					required = false) String sceneId,
			@ApiParam(name = "topo_id", value = "拓扑编号") @RequestParam(value = "topo_id",
					required = false) String topoId) {

		// 获取场景/拓扑数据
		SceneData sceneData = topoHandlerService.getSceneData(sceneId, topoId);

		// 处理态势数据
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);

		return new BaseRsp<>(situationTopo);
	}

	@CrossOrigin("*")
	@ApiOperation("想定方案绑定态势大屏")
	@PostMapping("plan_bind_situation")
	public BaseRsp<SituationPublishInstance> planBindSituationPublishInstance(
			@RequestBody PlanBindSituationReq planBindSituationReq) {

		SituationPublishInstance copy = BeanUtils.copy(planBindSituationReq,
				SituationPublishInstance.class);
		SituationPublishInstanceAddReq copy1 = BeanUtils.copy(copy,
				SituationPublishInstanceAddReq.class);
		// TODO 校验输入 任务方案 科目等id字段
		situationPublishInstanceService.checkPlan(copy1);
		// 处理态势数据
		// TODO 获取当前存入实例的id 提前生成一个 再存入
		situationPublishInstanceService.save(copy);
		return new BaseRsp<>(copy);
	}

	@CrossOrigin("*")
	@ApiOperation("接收任务指挥命令")
	@PostMapping("control")
	public BaseRsp<ControlRes>
			controlCmd(@RequestBody @Validated ControlCommandReq controlCommandReq) {

		log.info(String.format("成功接收指令：%s", JsonUtil.toJsonString(controlCommandReq)));

		return new BaseRsp<>(new ControlRes("1", "0"));
	}

}
