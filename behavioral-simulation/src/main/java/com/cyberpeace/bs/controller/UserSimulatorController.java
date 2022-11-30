package com.cyberpeace.bs.controller;

import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.IdReq;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.bs.service.bo.statistics.StatisticsRsp;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户行为流量发生")
@RestController
@RequestMapping("/bs/simulator")
// @RequiresAuthentication
public class UserSimulatorController {

	@Autowired
	UserSimulatorService userSimulatorService;

	@ApiOperation("用户流量发生分页列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<UserSimulatorBo>> listTraffic(PageReqBase pageReqBase) {
		ListRsp<UserSimulatorBo> res = userSimulatorService.pageSimulators(pageReqBase);
		return new BaseRsp<>(res);
	}

	@ApiOperation("配管创建仪表")
	@PostMapping("/create")
	public BaseRsp batchCreate(@RequestBody @Validated SimulatorAddBo addBo) {
		userSimulatorService.batchCreate(addBo);
		return new BaseRsp();
	}

	@ApiOperation("创建用户流量发生")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Validated UserSimulatorAddReq addReq) {
		userSimulatorService.create(addReq);
		return new BaseRsp();
	}

	@ApiOperation("批量删除用户流量发生")
	@DeleteMapping("/")
	public BaseRsp delete(@RequestBody @Validated IdListReq deleteReq) {
		userSimulatorService.batchDelete(deleteReq.getIds());
		return new BaseRsp();
	}

	@ApiOperation("启动用户流量")
	@PostMapping("/start")
	public BaseRsp start(@RequestBody @Validated IdReq idReq) {
		userSimulatorService.startSimulator(idReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("批量启动行为仪表")
	@PostMapping("/start_batch")
	public BaseRsp startBatch(@RequestBody @Validated IdListReq req) {
		userSimulatorService.batchStartSimulator(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("停止用户流量")
	@PostMapping("/stop")
	public BaseRsp stop(@RequestBody @Validated IdReq idReq) {
		userSimulatorService.stopSimulator(idReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("批量停止行为仪表")
	@PostMapping("/stop_batch")
	public BaseRsp stopBatch(@RequestBody @Validated IdListReq req){
		userSimulatorService.batchStopSimulator(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("行为仪表数量统计")
	@GetMapping("/statistics")
	public BaseRsp<StatisticsRsp> getSimulatorCount() {
		Integer count = userSimulatorService.countSimulators();
		return new BaseRsp<>(new StatisticsRsp(count));
	}

	@ApiOperation("行为实时仿真数量统计")
	@GetMapping("/task_count")
	public BaseRsp<StatisticsRsp> getSimulationTaskCount() {
		Integer count = userSimulatorService.countActiveSimulators();
		return new BaseRsp<>(new StatisticsRsp(count));
	}

	@ApiOperation("行为实时总带宽")
	@GetMapping("/bandwidth_count")
	public BaseRsp<Integer> getBandwidthStatistics() {
		return new BaseRsp<>(0);
	}

	@ApiOperation("行为仪表分页列表")
	@GetMapping("/flow_generators")
	public BaseRsp<ListRsp<BehaviorMeterBo>> getSimulatorList(MeterPageReq pageReq) {
		ListRsp<BehaviorMeterBo> res = userSimulatorService.pageBehaviorMeters(pageReq);
		return new BaseRsp<>(res);
	}

	@ApiOperation("试验名称下拉单")
	@GetMapping("/scene_name")
	public BaseRsp<List<SceneIdName>> getSceneList(){
		List<SceneIdName> res = userSimulatorService.getSceneList();
		return new BaseRsp<>(res);
	}

	@ApiOperation("行为仪表详情")
	@GetMapping("/detail")
	public BaseRsp<SimulatorDetail>
			getDetail(@ApiParam(value = "仪表resource_id", name = "resource_id") String resource_id) {
		SimulatorDetail detail = userSimulatorService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("关闭场景时删除相关仪表")
	@DeleteMapping("/scene")
	public BaseRsp deleteSceneRelated(@RequestBody @Validated IdReq idReq){
		userSimulatorService.deleteBySceneId(idReq.getResourceId());
		return new BaseRsp();
	}
}
