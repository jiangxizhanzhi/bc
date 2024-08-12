package com.cyberpeace.iov.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.IdListReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.service.DutService;
import com.cyberpeace.iov.service.bo.dut.DutAddReq;
import com.cyberpeace.iov.service.bo.dut.DutListBo;
import com.cyberpeace.iov.service.bo.dut.DutModifyReq;
import com.cyberpeace.iov.service.bo.dut.EnableReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "被测件管理")
@RestController
@RequestMapping("/iov/dut/")
@RequiresAuthentication
public class DutController {

	@Autowired
	private DutService dutService;

	@ApiOperation("被测件分页列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<DutListBo>> pageDuts(@ApiParam PageReqBase req,
			@ApiParam(value = "车型", name = "model") Integer model,
			@ApiParam(value = "域类型", name = "area") Integer area,
			@ApiParam(value = "状态", name = "status") Integer status) {
		ListRsp<DutListBo> res = dutService.pageDuts(req, model, area, status);
		return new BaseRsp<>(res);
	}

	@ApiOperation("被测件列表")
	@GetMapping("list")
	public BaseRsp<List<DutListBo>> listDuts(@ApiParam(value = "车型", name = "model") Integer model,
			@ApiParam(value = "域类型", name = "area") Integer area) {
		List<DutListBo> res = dutService.listDuts(model, area);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新建被测件")
	@PostMapping("")
	public BaseRsp create(@RequestBody DutAddReq req) {
		dutService.create(req);
		return new BaseRsp();
	}

	@ApiOperation("编辑被测件")
	@PutMapping("")
	public BaseRsp update(@RequestBody DutModifyReq req) {
		dutService.modify(req);
		return new BaseRsp();
	}

	@ApiOperation("删除被测件")
	@DeleteMapping("")
	public BaseRsp deleteBatch(@RequestBody IdListReq req) {
		dutService.deleteByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("启用或禁用")
	@PostMapping("/enable")
	public BaseRsp<Boolean> enable(@RequestBody EnableReq req) {
		dutService.enable(req);
		return new BaseRsp<>(req.getEnable());
	}

	@ApiOperation("被测件详情")
	@GetMapping("")
	public BaseRsp<DutListBo>
			getDetail(@ApiParam(value = "被测件id", name = "resource_id") String resource_id) {
		DutListBo bo = dutService.getDetail(resource_id);
		return new BaseRsp<>(bo);
	}
}
