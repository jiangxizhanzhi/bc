package com.cyberpeace.space.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseCategory;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseSystemMapper;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 空间策略防御Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class SpaceTacticDefenseServiceImpl implements SpaceTacticDefenseService {

	/**
	 * 映射
	 */
	@Autowired
	private SpaceTacticDefenseMapper mapper;

	@Autowired
	private SpaceTacticDefenseSystemMapper systemMapper;

	@Autowired
	private ToolService toolService;

	/**
	 * 类别映射器
	 */
	@Autowired
	private SpaceTacticDefenseCategoryMapper categoryMapper;

	/**
	 * utilService
	 */
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
	public ListRsp<DefenseListBo> page(SpaceTacticPageReq req) {

		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceTacticDefense> wrapper = req.req2QueryWrapper();
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
		Map<String, String> systemMap = Maps.newHashMap();
		List<String> systemIds = StreamApiUtils.map(iPage.getRecords(),
				SpaceTacticAttack::getSystemId);
		if (!systemIds.isEmpty()) {
			List<SpaceTacticDefenseSystem> systemList = systemMapper
					.selectBatchIds(systemIds);
			for (SpaceTacticDefenseSystem system : systemList) {
				systemMap.put(system.getResourceId(), system.getName());
			}
		}
		IPage<DefenseListBo> res = iPage.convert(defense -> {
			DefenseListBo bo = BeanUtils.copy(defense, DefenseListBo.class);
			bo.setCategory(categoryMap.get(bo.getCategoryId()));
			bo.setSystem(systemMap.get(bo.getSystemId()));
			List<String> toolIds = JsonUtil.toJsonObjectArray(defense.getTool(), String.class);
			bo.setToolCount(toolIds.size());
			JSONArray actions = JsonUtil.stringToArray(defense.getAction());
			bo.setActionCount(actions.size());
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
		SpaceTacticDefense defense = this.req2Entity(req);
		defense.setResourceId(IDGeneratorUtil.getID());
		mapper.insert(defense);
	}

	/**
	 * 更新防御
	 *
	 * @param req 请求体
	 */
	@Override
	public void updateDefense(DefenseModifyReq req) {
		// todo id name
		SpaceTacticDefense defense = this.req2Entity(req);
		defense.setResourceId(req.getResourceId());
		mapper.updateById(defense);
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
		SpaceTacticDefense defense = this.getOrException(resourceId);
		DefenseDetail detail = this.entity2Bo(defense);
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

	/**
	 * 列表由id列表
	 *
	 * @param defenseIds 防御id列表
	 * @return {@code List<SpaceTacticDefense> }
	 */
	@Override
	public List<SpaceTacticDefense> listByIds(List<String> defenseIds) {
		if(CollectionUtils.isEmpty(defenseIds)){
			return Lists.newArrayList();
		}
		return mapper.selectBatchIds(defenseIds);
	}

	/**
	 * 获取由id
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceTacticDefense }
	 */
	@Override
	public SpaceTacticDefense getById(String resourceId) {
		SpaceTacticDefense defense = mapper.selectById(resourceId);
		if(null == defense){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return defense;
	}

	/**
	 * 列表由类别id
	 *
	 * @param categoryId 类别id
	 * @return 列表<防御细节>
	 */
	@Override
	public List<DefenseDetail> listByCategoryId(String categoryId) {
		LambdaQueryWrapper<SpaceTacticDefense> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceTacticDefense::getCategoryId, categoryId);
		wrapper.orderByAsc(SpaceTacticDefense::getId);
		List<SpaceTacticDefense> defenseList = mapper.selectList(wrapper);
		return StreamApiUtils.map(defenseList, defense -> {
			DefenseDetail detail = BeanUtils.copy(defense, DefenseDetail.class);
//			detail.setTag(JsonUtil.toJsonObjectArray(defense.getTag(), String.class));
			return detail;
		});
	}

	/**
	 * 获取框架
	 *
	 * @return 动作框架层级
	 */
	@Override
	public SpaceFrameLevel getFrame() {
		SpaceFrameLevel level = new SpaceFrameLevel("", "");
		List<TacticDefenseCategoryBo> bos = this.listCategory();
		LambdaQueryWrapper<SpaceTacticDefense> wrapper = new LambdaQueryWrapper<>();
		wrapper.orderByAsc(SpaceTacticDefense::getId);
		List<SpaceTacticDefense> defenseList = mapper.selectList(wrapper);
		Map<String, List<SpaceTacticDefense>> defenseMap = StreamApiUtils.groupBy(defenseList,
				SpaceTacticDefense::getCategoryId);

		level.getChildren()
				.add(getStageFrame(SpaceCommonConstant.TacticStage.PRE, "事前阶段", defenseMap, bos));
		level.getChildren()
				.add(getStageFrame(SpaceCommonConstant.TacticStage.IN, "事中阶段", defenseMap, bos));
		level.getChildren()
				.add(getStageFrame(SpaceCommonConstant.TacticStage.POST, "事后阶段", defenseMap, bos));

		return level;
	}

	/**
	 * 获取阶段框架
	 *
	 * @param stage       阶段
	 * @param stageName   阶段名称
	 * @param defenseMap  防御地图
	 * @param categoryBos 类别 BOS
	 * @return {@code SpaceFrameLevel }
	 */
	private SpaceFrameLevel getStageFrame(Integer stage, String stageName,
			Map<String, List<SpaceTacticDefense>> defenseMap,
			List<TacticDefenseCategoryBo> categoryBos) {
		SpaceFrameLevel level = new SpaceFrameLevel(stage.toString(), stageName);
		for (TacticDefenseCategoryBo categoryBo : StreamApiUtils.filter(categoryBos,
				bo -> bo.getStage().equals(stage))) {
			SpaceFrameLevel categoryLevel = new SpaceFrameLevel(categoryBo.getResourceId(),
					categoryBo.getName());
			if (defenseMap.containsKey(categoryBo.getResourceId())) {
				for (SpaceTacticDefense defense : defenseMap.get(categoryBo.getResourceId())) {
					categoryLevel.getChildren()
							.add(new SpaceFrameLevel(defense.getResourceId(), defense.getName()));
				}
			}
			level.getChildren().add(categoryLevel);
		}
		return level;
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
		SpaceTacticDefense defense = new SpaceTacticDefense();
		defense.setName(req.getName());
//		defense.setTacticId(req.getTacticId());
		defense.setCategoryId(req.getCategoryId());
//		defense.setRole(req.getRole());
//		defense.setTag(JsonUtil.arrayToString(req.getTag()));
//		defense.setDescription(req.getDescription());
//		defense.setScene(req.getScene());
//		defense.setMonitor(req.getMonitor());
//		defense.setAnalyze(req.getAnalyze());
		defense.setTool(JsonUtil.arrayToString(req.getTool()));
		defense.setSource(req.getSource());
		defense.setSystemId(req.getSystemId());
		defense.setAction(JsonUtil.arrayToString(req.getAction()));
		return defense;
	}

	/**
	 * 实体 2 bo
	 *
	 * @param defense 防御
	 * @return {@code DefenseDetail }
	 */
	private DefenseDetail entity2Bo(SpaceTacticDefense defense) {
		DefenseDetail detail = BeanUtils.copy(defense, DefenseDetail.class);
//		detail.setTag(JsonUtil.toJsonObjectArray(defense.getTag(), String.class));

		SpaceTacticDefenseCategory category = categoryMapper.selectById(defense.getCategoryId());
		if (category != null) {
			detail.setCategoryName(category.getName());
		}
		// tool
		List<String> toolIds = JsonUtil.toJsonObjectArray(defense.getTool(), String.class);
		detail.setAction(JsonUtil.toJsonObjectArray(defense.getAction(), TacticAction.class));
		detail.setTool(toolService.listTacticTool(toolIds));
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
