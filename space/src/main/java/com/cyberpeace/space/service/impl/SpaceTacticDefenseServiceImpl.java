package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseCategory;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
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
public class SpaceTacticDefenseServiceImpl implements SpaceTacticDefenseService {

	@Autowired
	private SpaceTacticDefenseMapper mapper;

	@Autowired
	private SpaceTacticDefenseCategoryMapper categoryMapper;

	@Override
	public ListRsp<DefenseListBo> page(PageReqBase req) {

		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceTacticDefense> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(req.getPattern())) {
			wrapper.like(SpaceTacticDefense::getName, req.getPattern());
		}
		IPage<SpaceTacticDefense> iPage = mapper.selectPage(page, wrapper);
		List<String> categoryIds = StreamApiUtils.map(iPage.getRecords(),
				SpaceTacticDefense::getCategoryId);
		Map<String, String> categoryMap = Maps.newHashMap();
		if (!categoryIds.isEmpty()) {
			List<SpaceTacticDefenseCategory> categoryList = categoryMapper
					.selectBatchIds(categoryIds);
			for (SpaceTacticDefenseCategory category : categoryList) {
				categoryMap.put(category.getResourceId(), category.getName());
			}
		}
		IPage<DefenseListBo> res = iPage.convert(Defense -> {
			DefenseListBo bo = BeanUtils.copy(Defense, DefenseListBo.class);
			bo.setCategory(categoryMap.get(bo.getCategoryId()));
			return bo;
		});
		return PageUtil.getListResult(res);
	}

	@Override
	public void add(DefenseAddReq req) {
		SpaceTacticDefense Defense = this.req2Entity(req);
		Defense.setResourceId(IDGeneratorUtil.getID());
		mapper.insert(Defense);
	}

	@Override
	public void updateDefense(DefenseModifyReq req) {
		// todo id name
		SpaceTacticDefense Defense = this.req2Entity(req);
		Defense.setResourceId(req.getResourceId());
		mapper.updateById(Defense);
	}

	@Override
	public void deleteBatch(List<String> ids) {
		mapper.deleteBatchIds(ids);
	}

	@Override
	public DefenseDetail getDetail(String resourceId) {
		SpaceTacticDefense Defense = this.getOrException(resourceId);
		DefenseDetail detail = this.entity2Bo(Defense);
		return detail;
	}

	@Override
	public ListRsp<TacticDefenseCategoryBo> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();
		IPage<TacticDefenseCategoryBo> iPage = categoryMapper.page(page, reqBase.getPattern());
		return PageUtil.getListResult(iPage);
	}

	@Override
	public void addDefenseCategory(TacticDefenseCategoryAddReq req) {
		SpaceTacticDefenseCategory category = this.req2Entity(req);
		category.setResourceId(IDGeneratorUtil.getID());
		categoryMapper.insert(category);
	}

	@Override
	public void updateDefenseCategory(TacticDefenseCategoryModifyReq req) {
		SpaceTacticDefenseCategory category = this.req2Entity(req);
		category.setResourceId(req.getResourceId());
		categoryMapper.updateById(category);
	}

	@Override
	public void deleteCategory(List<String> ids) {
		categoryMapper.deleteBatchIds(ids);
	}

	@Override
	public DefenseCategoryBo getCategoryDetail(String resourceId) {
		SpaceTacticDefenseCategory category = this.getCategoryOrException(resourceId);
		DefenseCategoryBo bo = BeanUtils.copy(category, DefenseCategoryBo.class);
		return bo;
	}

	private SpaceTacticDefenseCategory req2Entity(TacticDefenseCategoryAddReq req) {
		SpaceTacticDefenseCategory category = new SpaceTacticDefenseCategory();
		category.setName(req.getName());
		category.setResourceId(IDGeneratorUtil.getID());
		return category;
	}

	private SpaceTacticDefense req2Entity(DefenseAddReq req) {
		SpaceTacticDefense Defense = new SpaceTacticDefense();
		Defense.setName(req.getName());
		Defense.setTacticId(req.getTacticId());
		Defense.setCategoryId(req.getCategoryId());
		Defense.setRole(req.getRole());
		Defense.setTag(JsonUtil.arrayToString(req.getTag()));
		Defense.setDescription(req.getDescription());
		Defense.setScene(req.getScene());
		Defense.setMonitor(req.getMonitor());
		Defense.setAnalyze(req.getAnalyze());
		Defense.setTool(JsonUtil.arrayToString(req.getTool()));
		return Defense;
	}

	private DefenseDetail entity2Bo(SpaceTacticDefense defense) {
		DefenseDetail detail = BeanUtils.copy(defense, DefenseDetail.class);
		detail.setTag(JsonUtil.toJsonObjectArray(defense.getTag(), String.class));
		detail.setTool(JsonUtil.toJsonObjectArray(defense.getTool(), TacticTool.class));

		SpaceTacticDefenseCategory category = categoryMapper.selectById(defense.getCategoryId());
		if (category != null) {
			detail.setCategoryName(category.getName());
		}

		return detail;
	}

	private SpaceTacticDefense getOrException(String resourceId) {
		SpaceTacticDefense defense = mapper.selectById(resourceId);
		if (null == defense) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return defense;
	}

	private SpaceTacticDefenseCategory getCategoryOrException(String resourceId) {
		SpaceTacticDefenseCategory category = categoryMapper.selectById(resourceId);
		if (null == category) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return category;
	}
}
