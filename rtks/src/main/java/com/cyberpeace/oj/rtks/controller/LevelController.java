package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.frame.*;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class LevelController {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private FramePorter porter;

	@ApiOperation("新增框架")
	@PostMapping()
	public BaseRsp<FrameOutput> create(@RequestBody @Validated FrameAddInput input) {
		Level frame = porter.getFrameFromInput(input);

		levelService.save(frame);

		return retrieve(frame.getResourceId());
	}

	@ApiOperation("修改框架")
	@PutMapping()
	public BaseRsp<FrameOutput> update(@RequestBody @Validated FrameModifyInput input) {
		String resourceId = input.getResourceId();

		levelService.getFrameById(resourceId);

		Level frame = porter.getFrameFromInput(input);

		levelService.updateById(frame);

		return retrieve(resourceId);
	}

	@ApiOperation("查询单条框架")
	@GetMapping()
	public BaseRsp<FrameOutput> retrieve(String resource_id) {
		Level frame = levelService.getFrameById(resource_id);
		return new BaseRsp<>(porter.getFrameOutput(frame));
	}

	@ApiOperation("删除框架")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		levelService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("框架列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<FrameOutput>> list(ListReq<Level> listReq) {
		IPage<Level> page = levelService.getFrames(listReq.toPageParam(), listReq.getKeyword());
		return PageUtil2.getListResult(page, porter::getFrameOutput);
	}

	@ApiOperation("指定框架层级数据的全量树形展示")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> getTree(String resource_id) {
		return new BaseRsp<>(porter.getLevelTree(resource_id));
	}

	@ApiOperation("新增层级")
	@PostMapping("/level")
	public BaseRsp<LevelOutput> createLevel(@RequestBody @Validated LevelAddInput input) {
		Level level = porter.getLevelFromInput(input);

		levelService.addLevel(level);

		return new BaseRsp<>(porter.getLevelOutput(level));
	}

	@ApiOperation("修改层级")
	@PutMapping("/level")
	public BaseRsp<LevelOutput> updateLevel(@RequestBody @Validated LevelModifyInput input) {
		Level level = porter.getLevelFromInput(input);

		levelService.modifyLevel(level);

		return new BaseRsp<>(porter.getLevelOutput(level));
	}

	@ApiOperation("删除层级")
	@DeleteMapping("/level")
	public BaseRsp<String> batchDeleteLevel(@RequestBody @Validated DeleteReq deleteReq) {
		levelService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

}
