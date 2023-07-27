package com.cyberpeace.bs.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.BehaviorCollection;
import com.cyberpeace.bs.dao.mapper.BehaviorCollectionMapper;
import com.cyberpeace.bs.service.BehaviorCollectionService;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.bs.service.bo.colletion.*;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BehaviorCollectionServiceImpl implements BehaviorCollectionService {

	@Autowired
	private BehaviorCollectionMapper behaviorCollectionMapper;

	@Autowired
	private LcService lcService;

	@Override
	public ListRsp<BehaviorCollectionListBo> list(Page page, Integer accessOs, String pattern) {
		// get collections
		IPage<BehaviorCollection> collectionIPage = behaviorCollectionMapper.pageCollection(page,
				accessOs, pattern);
		// convert
		collectionIPage.convert((collection -> {
			BehaviorCollectionListBo bo = new BehaviorCollectionListBo();
			copyCollection(collection, bo);
			bo.setBehaviorCount(collection.getBehaviorCount());
			bo.setStepCount(collection.getStepCount());
			bo.setCreateTime(collection.getCreateTime());
			return bo;
		}));
		return PageUtil.getListResult(collectionIPage);
	}

	@Override
	public BehaviorCollectionDetail getDetail(String resourceId) {
		BehaviorCollectionDetail res = new BehaviorCollectionDetail();
		BehaviorCollection collection = behaviorCollectionMapper.selectById(resourceId);
		if (null == collection) {
			return res;
		}
		copyCollection(collection, res);
		List<String> behaviorIds = JsonUtil.toJsonObjectArray(collection.getBehaviorList(),
				String.class);
		if (!behaviorIds.isEmpty()) {
			List<Behavior> behaviorList = lcService.getBehaviorListByIds(behaviorIds);
			res.setBehaviorList(behaviorList);
		}
		return res;
	}

	@Override
	public void create(CollectionAddReq addReq) {
		// check name
		checkName(addReq.getName(), "");

		BehaviorCollection collection = req2Entity(addReq);

		behaviorCollectionMapper.insert(collection);
	}

	@Override
	public void deleteByIds(List<String> ids) {
		behaviorCollectionMapper.deleteBatchIds(ids);
	}

	@Override
	public void modify(CollectionModifyReq modifyReq) {
		// check name
		checkName(modifyReq.getName(), modifyReq.getResourceId());

		BehaviorCollection collection = req2Entity(modifyReq);
		collection.setResourceId(modifyReq.getResourceId());
		behaviorCollectionMapper.updateById(collection);
	}

	@Override
	public List<Behavior> getBehaviorList(List<String> collectionIds) {
		// get collections
		List<BehaviorCollection> collectionList = behaviorCollectionMapper
				.selectBatchIds(collectionIds);
		// get behavior ids
		List<String> behaviorIds = collectionList.stream()
				.map(collection -> JsonUtil.toJsonObjectArray(collection.getBehaviorList(),
						String.class))
				.flatMap(Collection::stream).distinct().collect(Collectors.toList());
		if (behaviorIds.isEmpty()) {
			return Lists.newArrayList();
		}
		// get behaviors
		return lcService.getBehaviorListByIds(behaviorIds);
	}

	private <T extends BehaviorCollectionBase> T copyCollection(BehaviorCollection collection,
			T res) {
		res.setResourceId(collection.getResourceId());
		res.setName(collection.getName());
		res.setDescription(collection.getDescription());
		res.setAccessOs(collection.getAccessOs());
		return res;
	}

	private <T extends CollectionAddReq> BehaviorCollection req2Entity(T req) {
		BehaviorCollection collection = new BehaviorCollection();
		collection.setName(req.getName());
		collection.setDescription(req.getDescription());
		collection.setAccessOs(req.getAccessOs());
		collection.setBehaviorCount(req.getBehaviorList().size());
		collection.setStepCount(req.countStep());
		collection.setBehaviorList(JsonUtil.arrayToString(req.getBehaviorIds()));

		return collection;
	}

	private void checkName(String name, String resourceId) {
		if (behaviorCollectionMapper.existByName(name, resourceId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

}
