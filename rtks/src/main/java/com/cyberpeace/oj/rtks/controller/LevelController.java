package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.req.*;
import com.cyberpeace.oj.rtks.controller.res.FrameOutput;
import com.cyberpeace.oj.rtks.controller.res.LevelOutput;
import com.cyberpeace.oj.rtks.controller.res.LevelTreeOutput;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class LevelController {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("新增框架")
	@PostMapping()
	public BaseRsp<FrameOutput> addFrame(@RequestBody @Validated FrameAddInput frameAddInput) {
		Level frame = new Level();
		BeanUtils.copyProperties(frameAddInput, frame);

		levelService.save(frame);

		return new BaseRsp<>(new FrameOutput(frame));
	}

	@ApiOperation("修改框架")
	@PutMapping()
	public BaseRsp<FrameOutput>
			modifyTask(@RequestBody @Validated FrameModifyInput frameModifyInput) {
		Level frame = new Level();
		BeanUtils.copyProperties(frameModifyInput, frame);
		levelService.updateById(frame);
		return new BaseRsp<>(new FrameOutput(frame));
	}

	@ApiOperation("删除框架")
	@DeleteMapping()
	public BaseRsp<String> deleteFrame(@RequestBody @Validated DeleteReq deleteReq) {
		levelService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

	@ApiOperation("框架列表查询")
	@GetMapping()
	public BaseRsp<ListRsp<FrameOutput>> getFrames(ListReq<Level> listReq) {
		IPage<Level> page = levelService.getFrames(listReq.toPageParam(), listReq.getKeyword());
		return PageUtil2.getListResult(page, FrameOutput::new);
	}

	@ApiOperation("指定框架层级数据的全量树形展示")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> get(
			@ApiParam(name = "resource_id", value = "框架id", required = true) String resource_id) {
		LevelTreeOutput frame = levelService.getLevelTree(resource_id);
		return new BaseRsp<>(frame);
	}

	@ApiOperation("新增层级")
	@PostMapping("/level")
	public BaseRsp<LevelOutput> addLevel(@RequestBody @Validated LevelAddInput levelAddInput) {
		Level level = new Level();
		BeanUtils.copyProperties(levelAddInput, level);

		// 已经绑定了岗位的层级不允许新增子层级
		roleService.ensureLevelNotChildRole(level.getParent());
		levelService.addLevel(level);

		return new BaseRsp<>(new LevelOutput(level));
	}

	@ApiOperation("修改层级")
	@PutMapping("/level")
	public BaseRsp<LevelOutput>
			modifyLevel(@RequestBody @Validated LevelModifyInput levelModifyInput) {
		Level level = new Level();
		BeanUtils.copyProperties(levelModifyInput, level);
		levelService.modifyLevel(level);
		return new BaseRsp<>(new LevelOutput(level));
	}

	@ApiOperation("删除层级")
	@DeleteMapping("/level")
	public BaseRsp<String> deleteLevel(@RequestBody @Validated DeleteReq deleteReq) {
		levelService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>();
	}

}
