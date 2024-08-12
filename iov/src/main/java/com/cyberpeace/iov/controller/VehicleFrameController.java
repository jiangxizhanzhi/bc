package com.cyberpeace.iov.controller;

import com.cyberpeace.common.model.IdListReq;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.service.VehicleFrameService;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameAddReq;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameDetailBo;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameListBo;
import com.cyberpeace.iov.service.bo.frame.VehicleFrameModifyReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "车架管理")
@RestController
@RequestMapping("/iov/frame/")
@RequiresAuthentication
public class VehicleFrameController {

	@Autowired
	private VehicleFrameService vehicleFrameService;

	@ApiOperation("分页列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<VehicleFrameListBo>> pageFrame(@ApiParam PageReqBase req) {
		ListRsp<VehicleFrameListBo> res = vehicleFrameService.pageFrame(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增车架")
	@PostMapping("")
	public BaseRsp create(@RequestBody VehicleFrameAddReq req) {
		vehicleFrameService.create(req);
		return new BaseRsp();
	}

	@ApiOperation("编辑车架")
	@PutMapping("")
	public BaseRsp update(@RequestBody VehicleFrameModifyReq req) {
		vehicleFrameService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除车架")
	@DeleteMapping("")
	public BaseRsp deleteBatch(@RequestBody IdListReq req) {
		vehicleFrameService.deleteByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("车架详情")
	@GetMapping("")
	public BaseRsp<VehicleFrameDetailBo>
			getDetail(@ApiParam(value = "车架id", name = "resource_id") String resource_id) {
		VehicleFrameDetailBo bo = vehicleFrameService.getDetail(resource_id);
		return new BaseRsp<>(bo);
	}

}
