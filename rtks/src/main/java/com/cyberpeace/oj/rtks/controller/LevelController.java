package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.req.LevelAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/level")
public class LevelController {

	@Autowired
	private LevelService levelService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<Level> get(@ApiParam(name = "id", value = "查询id", required = true) String id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		Level level = levelService.getById(id);
		return new BaseRsp<>(level);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> add(@RequestBody @Validated LevelAddReq levelAddReq) {
		Level level = new Level();
		BeanUtils.copyProperties(levelAddReq, level);
		levelService.validateLevelId(level.getParent());
		levelService.save(level);
		return new BaseRsp<>();
	}
	// 指定框架新增层级
	// 指定框架修改层级
	// 指定框架删除层级
	// 指定框架层级数据的树形展示
	// 新增框架
	// 框架列表查询
	// 删除框架
	// 修改框架
	// 根据岗位名的模糊列表查询
	// 批量删除岗位
	// 编排岗位基础课
	// 编排岗位专业课
	// 修改岗位数据
	// 返回岗位所有的T数据
	// 返回岗位所有的K数据
	// 新增岗位
}
