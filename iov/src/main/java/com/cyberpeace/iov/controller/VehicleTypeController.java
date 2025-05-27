package com.cyberpeace.iov.controller;

import com.cyberpeace.iov.service.bo.frame.*;
import io.swagger.annotations.ApiModelProperty;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.service.VehicleTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@Api(tags = "车型管理")
@RestController
@RequestMapping("/iov/vehicle/type")
@RequiresAuthentication
public class VehicleTypeController {

	@Autowired
	private VehicleTypeService service;

	@ApiOperation("分页列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<VehicleTypeBo>> pageFrame(@ApiParam PageReqBase req) {
		ListRsp<VehicleTypeBo> res = service.pageType(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增车型")
	@PostMapping("")
	public BaseRsp create(@RequestBody VehicleTypeBo req) {
		service.create(req);
		return new BaseRsp();
	}

	@ApiOperation("编辑车型")
	@PutMapping("")
	public BaseRsp update(@RequestBody VehicleTypeBo req) {
		service.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除车型")
	@DeleteMapping("")
	public BaseRsp deleteBatch(@RequestBody IdListReq req) {
		service.deleteByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("车型详情")
	@GetMapping("")
	public BaseRsp<VehicleTypeBo>
			getDetail(@ApiParam(value = "车型id", name = "resource_id") String resource_id) {
        VehicleTypeBo bo = service.getDetail(resource_id);
		return new BaseRsp<>(bo);
	}

	@ApiOperation("车型列表")
	@GetMapping("/list")
	public BaseRsp<List<VehicleTypeBo>> list() {
		List<VehicleTypeBo> list = service.listBos();
		return new BaseRsp<>(list);
	}
}
