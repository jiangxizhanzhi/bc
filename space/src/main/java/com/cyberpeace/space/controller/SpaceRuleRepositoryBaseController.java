package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.IDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.IdListReq;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public abstract class SpaceRuleRepositoryBaseController<S extends IService<E>, E extends SpaceBaseTable> {

	@Autowired
	private S baseService;

	protected Class<E> entityClass = currentModelCls();

	@SuppressWarnings("unchecked")
	private Class<E> currentModelCls() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
	}

	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<E>> page(PageReqBase req) {
		IPage<E> iPage = baseService.page(req.toPageParam());
		ListRsp<E> res = PageUtil.getListResult(iPage);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody E req) {
//		req.setId(null);
		req.setResourceId(IDGeneratorUtil.getID());
		baseService.getBaseMapper().insert(req);
		return new BaseRsp();
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody E req) {
//		req.setId(null);
		baseService.updateById(req);
		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		baseService.removeByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<E>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		E res = baseService.getById(resource_id);
//		res.setId(null);
		return new BaseRsp<>(res);
	}

}
