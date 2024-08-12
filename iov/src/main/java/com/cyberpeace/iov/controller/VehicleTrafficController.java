package com.cyberpeace.iov.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.service.VehicleTrafficService;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficAddReq;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficDetailBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficListBo;
import com.cyberpeace.iov.service.bo.traffic.VehicleTrafficModifyReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "车辆流量管理")
@RestController
@RequestMapping("/iov/traffic/")
@RequiresAuthentication
public class VehicleTrafficController {

	@Autowired
	private VehicleTrafficService vehicleTrafficService;

	@ApiOperation("分页列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<VehicleTrafficListBo>> pageFrame(@ApiParam PageReqBase req) {
		ListRsp<VehicleTrafficListBo> res = vehicleTrafficService.pageTraffic(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增车辆流量")
	@PostMapping("")
	public BaseRsp create(@RequestBody VehicleTrafficAddReq req) {
		vehicleTrafficService.create(req);
		return new BaseRsp();
	}

	@ApiOperation("编辑车辆流量")
	@PutMapping("")
	public BaseRsp update(@RequestBody VehicleTrafficModifyReq req) {
		vehicleTrafficService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除车辆流量")
	@DeleteMapping("")
	public BaseRsp deleteBatch(@RequestBody IdListReq req) {
		vehicleTrafficService.deleteByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("车辆流量详情")
	@GetMapping("")
	public BaseRsp<VehicleTrafficDetailBo>
			getDetail(@ApiParam(value = "车架id", name = "resource_id") String resource_id) {
		VehicleTrafficDetailBo bo = vehicleTrafficService.getDetail(resource_id);
		return new BaseRsp<>(bo);
	}

}