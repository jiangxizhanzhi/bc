package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseCategory;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceTacticDefenseServiceImpl implements SpaceTacticDefenseService {

	/**
	 * 映射
	 */
	@Autowired
	private SpaceTacticDefenseMapper mapper;

	/**
	 * 类别映射器
	 */
	@Autowired
	private SpaceTacticDefenseCategoryMapper categoryMapper;

	@Autowired
	/**
	 * utilService
	 */
	private UtilService utilService;

	/**
	 * 分页
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<DefenseListBo> }
	 */
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

	/**
	 * 新增
	 *
	 * @param req 请求体
	 */
	@Override
	public void add(DefenseAddReq req) {
		SpaceTacticDefense Defense = this.req2Entity(req);
		Defense.setResourceId(IDGeneratorUtil.getID());
		mapper.insert(Defense);
	}

	/**
	 * 更新防御
	 *
	 * @param req 请求体
	 */
	@Override
	public void updateDefense(DefenseModifyReq req) {
		// todo id name
		SpaceTacticDefense Defense = this.req2Entity(req);
		Defense.setResourceId(req.getResourceId());
		mapper.updateById(Defense);
	}

	/**
	 * 删除批量
	 *
	 * @param ids id列表
	 */
	@Override
	public void deleteBatch(List<String> ids) {
		mapper.deleteBatchIds(ids);
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code DefenseDetail }
	 */
	@Override
	public DefenseDetail getDetail(String resourceId) {
		SpaceTacticDefense Defense = this.getOrException(resourceId);
		DefenseDetail detail = this.entity2Bo(Defense);
		return detail;
	}

	/**
	 * 分页类别
	 *
	 * @param reqBase 请求体基础
	 * @return {@code ListRsp<TacticDefenseCategoryBo> }
	 */
	@Override
	public ListRsp<TacticDefenseCategoryBo> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();
		IPage<TacticDefenseCategoryBo> iPage = categoryMapper.page(page, reqBase.getPattern());
		for (TacticDefenseCategoryBo bo : iPage.getRecords()) {
			bo.setCover(utilService.handlerFileUrl(bo.getCover()));
		}
		return PageUtil.getListResult(iPage);
	}

	/**
	 * 新增防御类别
	 *
	 * @param req 请求体
	 */
	@Override
	public void addDefenseCategory(TacticDefenseCategoryAddReq req) {
		SpaceTacticDefenseCategory category = this.req2Entity(req);
		category.setResourceId(IDGeneratorUtil.getID());
		categoryMapper.insert(category);
	}

	/**
	 * 更新防御类别
	 *
	 * @param req 请求体
	 */
	@Override
	public void updateDefenseCategory(TacticDefenseCategoryModifyReq req) {
		SpaceTacticDefenseCategory category = this.req2Entity(req);
		category.setResourceId(req.getResourceId());
		categoryMapper.updateById(category);
	}

	/**
	 * 删除类别
	 *
	 * @param ids id列表
	 */
	@Override
	public void deleteCategory(List<String> ids) {
		categoryMapper.deleteBatchIds(ids);
	}

	/**
	 * 获取类别详情
	 *
	 * @param resourceId 资源id
	 * @return {@code DefenseCategoryBo }
	 */
	@Override
	public DefenseCategoryBo getCategoryDetail(String resourceId) {
		SpaceTacticDefenseCategory category = this.getCategoryOrException(resourceId);
		DefenseCategoryBo bo = BeanUtils.copy(category, DefenseCategoryBo.class);
		return bo;
	}

	/**
	 * 列表类别
	 *
	 * @return {@code List<TacticDefenseCategoryBo> }
	 */
	@Override
    public List<TacticDefenseCategoryBo> listCategory() {
		// in order
		return categoryMapper.listAllInOrder();

    }

	@Override
	public List<SpaceTacticDefense> listByIds(List<String> defenseIds) {
		if(CollectionUtils.isEmpty(defenseIds)){
			return Lists.newArrayList();
		}
		return mapper.selectBatchIds(defenseIds);
	}

	@Override
	public SpaceTacticDefense getById(String resourceId) {
		SpaceTacticDefense defense = mapper.selectById(resourceId);
		if(null == defense){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return defense;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return {@code SpaceTacticDefenseCategory }
	 */
	private SpaceTacticDefenseCategory req2Entity(TacticDefenseCategoryAddReq req) {
		SpaceTacticDefenseCategory category = new SpaceTacticDefenseCategory();
		category.setName(req.getName());
		category.setResourceId(IDGeneratorUtil.getID());
		category.setStage(req.getStage());
		category.setCover(req.getCover());
		return category;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return {@code SpaceTacticDefense }
	 */
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

	/**
	 * 实体 2 bo
	 *
	 * @param defense 防御
	 * @return {@code DefenseDetail }
	 */
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

	/**
	 * 获取或例外
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceTacticDefense }
	 */
	private SpaceTacticDefense getOrException(String resourceId) {
		SpaceTacticDefense defense = mapper.selectById(resourceId);
		if (null == defense) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return defense;
	}

	/**
	 * 获取类别或例外
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceTacticDefenseCategory }
	 */
	private SpaceTacticDefenseCategory getCategoryOrException(String resourceId) {
		SpaceTacticDefenseCategory category = categoryMapper.selectById(resourceId);
		if (null == category) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return category;
	}
}
