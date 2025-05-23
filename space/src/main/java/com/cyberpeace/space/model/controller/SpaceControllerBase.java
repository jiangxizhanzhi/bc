package com.cyberpeace.space.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.model.SpaceBoBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.IdListReq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public abstract class SpaceControllerBase<S extends IService<E>, E extends SpaceBaseTable, B extends SpaceBoBase, R extends SpacePageReqBase> {

	@Autowired
	protected S baseService;


	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<B>> page(R req) {
		LambdaQueryWrapper<E> wrapper = req.req2QueryWrapper();
		IPage<E> iPage = baseService.page(req.toPageParam(), wrapper);
		Map<String, IdAndName> relatedMap = getRelatedMap(iPage.getRecords());
		iPage.convert(e -> e.entity2Bo(relatedMap));
		ListRsp<B> res = PageUtil.getListResult(iPage);
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody B req) {
		req.setResourceId(IDGeneratorUtil.getID());
		E entity = (E) req.bo2Entity();
		entity.setBuiltin(false);
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		baseService.save(entity);
		return new BaseRsp();
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody B req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		E old = baseService.getById(req.getResourceId());
		if(null == old){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(null != old.getBuiltin() && old.getBuiltin()){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		E entity = (E) req.bo2Entity();
		entity.setBuiltin(false);
		this.checkNameDuplication(entity);
		this.checkValue(entity);
		baseService.updateById(entity);
		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		List<E> eList = baseService.listByIds(req.getIds());
		for (E e : eList) {
			if(null != e.getBuiltin() && e.getBuiltin()){
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}
		baseService.removeByIds(req.getIds());
		return new BaseRsp();
	}

	@ApiOperation("详情")
	@GetMapping("")
	public BaseRsp<B>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		E res = baseService.getById(resource_id);
		if (null == res) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		B bo = (B) res.entity2Bo(getRelatedMap(Lists.newArrayList(res)));
		return new BaseRsp<>(bo);
	}

	@ApiOperation("列出全部")
	@GetMapping("/list_all")
	public BaseRsp<List<B>> listAll() {
		LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(E::getId);
		List<E> eList = baseService.list(wrapper);
		List<B> res = Lists.newArrayList();
		for (E e : eList) {
			res.add((B) e.entity2Bo(getRelatedMap(eList)));
		}
		return new BaseRsp<>(res);
	}

	public boolean checkNameDuplication(E entity) {
		CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(E::getName, entity.getName());
		E one = baseService.getOne(wrapper);
		if (null != one && (StringUtils.isBlank(entity.getResourceId())
				|| !one.getResourceId().equals(entity.getResourceId()))) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
		return true;
	}

	public void checkValue(E entity){
	}

	public void checkPercentageValue(Integer value){
		if(value > 100){
			throw new BusinessException(SpaceError.PERCENTAGE_LARGER_THAN_100);
		}
	}

	protected Map<String, IdAndName> getRelatedMap(List<E> eList) {
		return Maps.newHashMap();
	}

}
