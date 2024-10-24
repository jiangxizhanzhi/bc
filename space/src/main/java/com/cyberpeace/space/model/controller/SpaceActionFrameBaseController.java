package com.cyberpeace.space.model.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import com.cyberpeace.space.service.context.IdListReq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public abstract class SpaceActionFrameBaseController<S extends IService<E>, E extends SpaceFrameBaseTable, B extends SpaceFrameBoBase, R extends FrameBasePageReq> {

	@Autowired
	protected S baseService;

	protected Class<E> entityClass = currentModelCls();

	@SuppressWarnings("unchecked")
	private Class<E> currentModelCls() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
	}

	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<B>> page(R req) {
		LambdaQueryWrapper<E> wrapper = req.req2QueryWrapper();
		IPage<E> iPage = baseService.page(req.toPageParam(), wrapper);
		iPage.convert(e->e.entity2Bo(this.getParentMap(iPage.getRecords())));
		ListRsp<B> res = PageUtil.getListResult(iPage);
		return new BaseRsp<>(res);
	}


	@ApiOperation("全部")
	@GetMapping("/list_all")
	public BaseRsp<List<B>> listAll() {
		List<E> eList = baseService.list();
		List<B> res = StreamApiUtils.map(eList, e -> (B) e.entity2Bo(Maps.newHashMap()));
		return new BaseRsp<>(res);
	}

	@ApiOperation("新增")
	@PostMapping("")
	public BaseRsp addAttack(@RequestBody B req) {
		req.setResourceId(IDGeneratorUtil.getID());
		E entity = (E) req.bo2Entity();
		this.checkNameDuplication(entity);
		baseService.getBaseMapper().insert(entity);
		return new BaseRsp();
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody B req) {
		E entity = (E) req.bo2Entity();
		this.checkNameDuplication(entity);
		baseService.updateById(entity);
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
	public BaseRsp<B>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		E e = baseService.getById(resource_id);
		if(null == e){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		B bo = (B) e.entity2Bo(getParentMap(Lists.newArrayList(e)));
		return new BaseRsp<>(bo);
	}

	public void checkNameDuplication(E entity) {
		CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		LambdaQueryWrapper<E> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(E::getName, entity.getName());
		E one = baseService.getOne(wrapper);
		if (null != one && (StringUtils.isBlank(entity.getResourceId())
				|| !one.getResourceId().equals(entity.getResourceId()))) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	private Map<String, IdAndName> getParentMap(List<E> list){
		Map<String, IdAndName> res = Maps.newHashMap();
		this.addIdAndName(res, list);
		return res;
	}

	protected void createIdAndName(List<? extends SpaceFrameBaseTable> entities, Map<String, IdAndName> parentMap){
		for (SpaceFrameBaseTable e : entities) {
			parentMap.put(e.getResourceId(), new IdAndName(e.getResourceId(), e.getName()));
		}
	}

	protected void addIdAndName(Map<String, IdAndName> parentMap, List<E> entityList){

	}

}
