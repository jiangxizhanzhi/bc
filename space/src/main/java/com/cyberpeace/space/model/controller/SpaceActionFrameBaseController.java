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
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.dao.entity.SpaceActionRelation;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import com.cyberpeace.space.model.req.FrameBasePageReq;
import com.cyberpeace.space.service.SpaceActionRelationService;
import com.cyberpeace.space.service.context.IdListReq;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class SpaceActionFrameBaseController<S extends IService<E>, E extends SpaceFrameBaseTable, B extends SpaceFrameBoBase, R extends FrameBasePageReq> {

	@Autowired
	protected S baseService;

	@Autowired
	protected SpaceActionRelationService relationService;

	@Autowired
	protected TransactionTemplate transactionTemplate;

	@ApiOperation("列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<B>> page(R req) {
		LambdaQueryWrapper<E> wrapper = req.req2QueryWrapper();
		String upperId = req.getUpperId();
		if (StringUtils.isNotBlank(upperId)) {
			List<String> ids = relationService.getLowerIdsByUpperId(upperId);
			if (ids.isEmpty()) {
				return new BaseRsp<>(PageUtil.getNullResult());
			}
			wrapper.in(E::getResourceId, ids);
		}
		IPage<E> iPage = baseService.page(req.toPageParam(), wrapper);
		iPage.convert(e -> e.entity2Bo(this.getRelatedMap(iPage.getRecords())));
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
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.getBaseMapper().insert(entity);
				relationService.createRelation(getUpperIds(req), req.getResourceId());
			}
		});

		return new BaseRsp();
	}

	@ApiOperation("更新")
	@PatchMapping("")
	public BaseRsp updateA(@RequestBody B req) {
		if (StringUtils.isBlank(req.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		E entity = (E) req.bo2Entity();
		this.checkNameDuplication(entity);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.updateById(entity);
				relationService.updateRelation(getUpperIds(req), req.getResourceId());
			}
		});

		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("")
	public BaseRsp deleteA(@RequestBody IdListReq req) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseService.removeByIds(req.getIds());
				relationService.deleteRelationsByIds(req.getIds());
			}
		});
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
		B bo = (B) e.entity2Bo(getRelatedMap(Lists.newArrayList(e)));
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

	private Map<String, List<IdAndName>> getRelatedMap(List<E> eList){
		Map<String, List<IdAndName>> res = Maps.newHashMap();
		if (eList.isEmpty()) {
			return res;
		}
		List<String> lowerIds = StreamApiUtils.map(eList, E::getResourceId);
		LambdaQueryWrapper<SpaceActionRelation> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SpaceActionRelation::getLowerId, lowerIds);
		List<SpaceActionRelation> relations = relationService.list(wrapper);
		if (relations.isEmpty()) {
			return res;
		}
		List<String> upperIds = StreamApiUtils.map(relations, SpaceActionRelation::getUpperId);
		List<? extends SpaceFrameBaseTable> upperList = this.getUpperRelated(upperIds);

		Map<String, IdAndName> upperMap = Maps.newHashMap();
		for (SpaceFrameBaseTable e : upperList) {
			upperMap.put(e.getResourceId(), new IdAndName(e.getResourceId(), e.getName()));
		}
		// 排序，使结果一致
		relations = relations.stream().sorted(Comparator.comparing(SpaceActionRelation::getId)).collect(Collectors.toList());

		for (SpaceActionRelation relation : relations) {
			String lowerId = relation.getLowerId();
			String upperId = relation.getUpperId();
			res.putIfAbsent(lowerId, Lists.newArrayList());
			res.get(lowerId).add(upperMap.get(upperId));
		}
		return res;
	}

	protected List<? extends SpaceFrameBaseTable> getUpperRelated(List<String> upperIds) {
		return Lists.newArrayList();
	}

	protected List<String> getUpperIds(B req){
		return Lists.newArrayList();
	}

}
