package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.req.FrameAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Frame;
import com.cyberpeace.oj.rtks.service.FrameService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class FrameController {

	@Autowired
	private FrameService frameService;

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<Frame> get(@ApiParam(name = "id", value = "查询id", required = true) String id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		Frame frame = frameService.getById(id);
		return new BaseRsp<>(frame);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> add(@RequestBody @Validated FrameAddReq frameAddReq) {
		Frame frame = new Frame();
		BeanUtils.copyProperties(frameAddReq, frame);
		frameService.validateFrameId(frame.getParent());
		frameService.save(frame);
		return new BaseRsp<>();
	}

}
