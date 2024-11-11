package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.context.TacticTool;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticAttackCategory;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.service.SpaceTacticAttackService;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceTacticAttackServiceImpl implements SpaceTacticAttackService {

	@Autowired
	private SpaceTacticAttackMapper mapper;

	@Autowired
	private SpaceTacticAttackCategoryMapper categoryMapper;

	@Autowired
	private ToolService toolService;

	@Override
	public ListRsp<AttackListBo> pageAttack(PageReqBase req) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceTacticAttack> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(SpaceTacticAttack::getName, req.getPattern());
		}
		IPage<SpaceTacticAttack> iPage = mapper.selectPage(page, wrapper);
		List<String> categoryIds = StreamApiUtils.map(iPage.getRecords(),
				SpaceTacticAttack::getCategoryId);
		Map<String, String> categoryMap = Maps.newHashMap();
		if (!categoryIds.isEmpty()) {
			List<SpaceTacticAttackCategory> categoryList = categoryMapper
					.selectBatchIds(categoryIds);
			for (SpaceTacticAttackCategory category : categoryList) {
				categoryMap.put(category.getResourceId(), category.getName());
			}
		}
		IPage<AttackListBo> res = iPage.convert(attack -> {
			AttackListBo bo = BeanUtils.copy(attack, AttackListBo.class);
			bo.setCategory(categoryMap.get(bo.getCategoryId()));
			return bo;
		});
		return PageUtil.getListResult(res);
	}

	@Override
	public void addAttack(AttackAddReq req) {
		SpaceTacticAttack attack = this.req2Entity(req);
		attack.setResourceId(IDGeneratorUtil.getID());
		mapper.insert(attack);
	}

	@Override
	public void updateAttack(AttackModifyReq req) {
		// todo id name
		SpaceTacticAttack attack = this.req2Entity(req);
		attack.setResourceId(req.getResourceId());
		mapper.updateById(attack);

	}

	@Override
	public void deleteBatch(List<String> ids) {
		mapper.deleteBatchIds(ids);
	}

	@Override
	public AttackDetail getDetail(String resourceId) {
		SpaceTacticAttack attack = this.getOrException(resourceId);
		AttackDetail detail = this.entity2Bo(attack);
		return detail;
	}

	@Override
	public ListRsp<TacticAttackCategoryBo> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();
		IPage<TacticAttackCategoryBo> iPage = categoryMapper.page(page, reqBase.getPattern());
		return PageUtil.getListResult(iPage);
	}

	@Override
	public void addAttackCategory(TacticAttackCategoryAddReq req) {
		SpaceTacticAttackCategory category = this.req2Entity(req);
		category.setResourceId(IDGeneratorUtil.getID());
		categoryMapper.insert(category);
	}

	@Override
	public void updateAttackCategory(TacticAttackCategoryModifyReq req) {
		SpaceTacticAttackCategory category = this.req2Entity(req);
		category.setResourceId(req.getResourceId());
		categoryMapper.updateById(category);
	}

	@Override
	public void deleteCategory(List<String> ids) {
		categoryMapper.deleteBatchIds(ids);
	}

	private SpaceTacticAttackCategory req2Entity(TacticAttackCategoryAddReq req) {
		SpaceTacticAttackCategory category = new SpaceTacticAttackCategory();
		category.setName(req.getName());
		category.setResourceId(IDGeneratorUtil.getID());
		return category;
	}

	private SpaceTacticAttack req2Entity(AttackAddReq req) {
		SpaceTacticAttack attack = new SpaceTacticAttack();
		attack.setName(req.getName());
		attack.setTacticId(req.getTacticId());
		attack.setCategoryId(req.getCategoryId());
		attack.setRole(req.getRole());
		attack.setTag(JsonUtil.arrayToString(req.getTag()));
		attack.setDescription(req.getDescription());
		attack.setScene(req.getScene());
		attack.setFeature(req.getFeature());
		attack.setAction(JsonUtil.arrayToString(req.getAction()));
		attack.setTool(JsonUtil.arrayToString(req.getTool()));
		return attack;
	}

	private AttackDetail entity2Bo(SpaceTacticAttack attack) {
		AttackDetail detail = BeanUtils.copy(attack, AttackDetail.class);
		detail.setTag(JsonUtil.toJsonObjectArray(attack.getTag(), String.class));
		detail.setAction(JsonUtil.toJsonObjectArray(attack.getAction(), TacticAction.class));
		detail.setTool(JsonUtil.toJsonObjectArray(attack.getTool(), TacticTool.class));

		SpaceTacticAttackCategory category = categoryMapper.selectById(attack.getCategoryId());
		if (category != null) {
			detail.setCategoryName(category.getName());
		}
		// tool
		List<String> toolIds = JsonUtil.toJsonObjectArray(attack.getTool(), String.class);
		detail.setTool(toolService.listTacticTool(toolIds));
		return detail;
	}

	private SpaceTacticAttack getOrException(String resourceId) {
		SpaceTacticAttack attack = mapper.selectById(resourceId);
		if (null == attack) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return attack;
	}
}
