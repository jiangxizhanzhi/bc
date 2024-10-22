package com.cyberpeace.space.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.model.req.IdReq;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.strategy.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "知识空间-知识库-策略库")
@RestController
@RequestMapping("/space/strategy")
@Slf4j
public class SpaceStrategyController {

	@Autowired
	private SpaceStrategyService spaceStrategyService;

	@ApiOperation("获取框架")
	@GetMapping("/frame")
	public BaseRsp<List<SpaceStrategyFrameBo>> getFrame() {
		SpaceStrategyFrameBo bo = spaceStrategyService.getFrame();
		return new BaseRsp<>(bo.getChildren());
	}

	@ApiOperation("增加下一级")
	@PostMapping("/frame")
	public BaseRsp addFrame(@RequestBody SpaceStrategyFrameAddReq req) {
		spaceStrategyService.addFrame(req);
		return new BaseRsp();
	}

	@ApiOperation("编辑框架")
	@PatchMapping("/frame")
	public BaseRsp modifyFrame(@RequestBody SpaceStrategyFrameModifyReq req) {
		spaceStrategyService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除框架")
	@DeleteMapping("/frame")
	public BaseRsp deleteFrame(@RequestBody IdReq idReq) {
		spaceStrategyService.deleteFrame(idReq.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("获取策略列表")
	@GetMapping("/list")
	public BaseRsp<List<SpaceStrategySimple>> getStrategies(
			@ApiParam(value = "框架id", name = "frame_id") String frame_id,
			@ApiParam(value = "名称", name = "name") String name) {
		List<SpaceStrategySimple> res = spaceStrategyService.listStrategiesByFrame(frame_id, name);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新建策略")
	@PostMapping("")
	public BaseRsp addStrategy(@RequestBody SpaceStrategyAddReq req) {
		spaceStrategyService.addStrategy(req);
		return new BaseRsp();
	}

	@ApiOperation("修改策略")
	@PatchMapping("")
	public BaseRsp updateStrategy(@RequestBody SpaceStrategyModifyReq req) {
		spaceStrategyService.updateStrategy(req);
		return new BaseRsp();
	}

	@ApiOperation("删除策略")
	@DeleteMapping("")
	public BaseRsp deleteStrategy(@RequestBody IdReq req) {
		spaceStrategyService.deleteStrategy(req.getResourceId());
		return new BaseRsp();
	}

	@ApiOperation("查看策略详情")
	@GetMapping("")
	public BaseRsp<SpaceStrategyBo>
			getDetail(@ApiParam(value = "策略id", name = "resource_id") String resource_id) {
		SpaceStrategyBo res = spaceStrategyService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

}
