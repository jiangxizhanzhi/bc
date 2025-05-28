package com.cyberpeace.space.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticAttackCategory;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseSystemMapper;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.SpaceTacticAttackService;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartCategory;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;
import com.cyberpeace.space.service.context.index.KnowledgeChartLink;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 空间战法攻击Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceTacticAttackService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceTacticAttackServiceImpl extends ServiceImpl<SpaceTacticAttackMapper, SpaceTacticAttack> implements SpaceTacticAttackService {

	@Autowired
	private SpaceTacticAttackMapper mapper;

	@Autowired
	private SpaceTacticAttackCategoryMapper categoryMapper;

	@Autowired
	private SpaceTacticDefenseSystemMapper systemMapper;

	@Autowired
	private ToolService toolService;

	@Autowired
	private SpaceStrategyService strategyService;

	@Autowired
	private SpaceTacticAttackMapper spaceTacticAttackMapper;

	/**
	 * 分页攻击
	 *
	 * @param req 请求体
	 * @return
	 * @throws
	 * @see SpaceTacticPageReq
	 * @see ListRsp<AttackListBo>
	 */
	@Override
	public ListRsp<AttackListBo> pageAttack(SpaceTacticPageReq req) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceTacticAttack> wrapper = req.req2QueryWrapper();
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
		IPage<AttackListBo> res = iPage.convert(attack -> {
			AttackListBo bo = BeanUtils.copy(attack, AttackListBo.class);
			bo.setCategory(categoryMap.get(bo.getCategoryId()));
			bo.setSystem(systemMap.get(bo.getSystemId()));
			List<String> toolIds = JsonUtil.toJsonObjectArray(attack.getTool(), String.class);
			bo.setToolCount(toolIds.size());
			JSONArray actions = JsonUtil.stringToArray(attack.getAction());
			bo.setActionCount(actions.size());
			return bo;
		});
		return PageUtil.getListResult(res);
	}

	/**
	 * 新增攻击
	 *
	 * @param req 请求体
	 * @throws
	 * @see AttackAddReq
	 */
	@Override
	public void addAttack(AttackAddReq req) {
		SpaceTacticAttack attack = this.req2Entity(req);
		attack.setResourceId(IDGeneratorUtil.getID());
		mapper.insert(attack);
	}

	/**
	 * 更新攻击
	 *
	 * @param req 请求体
	 * @throws
	 * @see AttackModifyReq
	 */
	@Override
	public void updateAttack(AttackModifyReq req) {
		// todo id name
		SpaceTacticAttack attack = this.req2Entity(req);
		attack.setResourceId(req.getResourceId());
		mapper.updateById(attack);

	}

	/**
	 * 删除批量
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void deleteBatch(List<String> ids) {
		mapper.deleteBatchIds(ids);
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return 攻击详情
	 * @throws
	 * @see String
	 * @see AttackDetail
	 */
	@Override
	public AttackDetail getDetail(String resourceId) {
		SpaceTacticAttack attack = this.getOrException(resourceId);
		AttackDetail detail = this.entity2Bo(attack);
		return detail;
	}

	/**
	 * 分页类别
	 *
	 * @param reqBase 请求体基础
	 * @return 列表RSP<战法攻击类别博>
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<TacticAttackCategoryBo>
	 */
	@Override
	public ListRsp<TacticAttackCategoryBo> pageCategory(PageReqBase reqBase) {
		Page page = reqBase.toPageParam();
		IPage<TacticAttackCategoryBo> iPage = categoryMapper.page(page, reqBase.getPattern());
		return PageUtil.getListResult(iPage);
	}

	/**
	 * 新增攻击类别
	 *
	 * @param req 请求体
	 * @throws
	 * @see TacticAttackCategoryAddReq
	 */
	@Override
	public void addAttackCategory(TacticAttackCategoryAddReq req) {
		SpaceTacticAttackCategory category = this.req2Entity(req);
		category.setResourceId(IDGeneratorUtil.getID());
		categoryMapper.insert(category);
	}

	/**
	 * 更新攻击类别
	 *
	 * @param req 请求体
	 * @throws
	 * @see TacticAttackCategoryModifyReq
	 */
	@Override
	public void updateAttackCategory(TacticAttackCategoryModifyReq req) {
		SpaceTacticAttackCategory category = this.req2Entity(req);
		category.setResourceId(req.getResourceId());
		categoryMapper.updateById(category);
	}

	/**
	 * 删除类别
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void deleteCategory(List<String> ids) {
		categoryMapper.deleteBatchIds(ids);
	}

	/**
	 * 获取战法攻击图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart getTacticAttackChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.TACTIC_ATTACK,
				SpaceKTypeEnum.TACTIC.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(
				SpaceKTypeEnum.TACTIC_ATTACK);
		chart.getCategories().add(baseCategory);
		for (SpaceTacticAttack attack : mapper.selectList(null)) {
			KnowledgeChartItem item = new KnowledgeChartItem(3);
			item.setId(attack.getResourceId());
			item.setName(attack.getName());
			item.setType(SpaceKTypeEnum.TACTIC_ATTACK.getType());
			item.setCategory(baseItem.getId());
			chart.getData().add(item);
			KnowledgeChartLink link = new KnowledgeChartLink(baseItem.getId(),
					attack.getResourceId());
			chart.getLinks().add(link);
		}
//		for (SpaceTacticAttackCategory category : categoryMapper.selectList(null)) {
//			KnowledgeChartItem item = new KnowledgeChartItem(3);
//			item.setId(category.getResourceId());
//			item.setName(category.getName());
//			item.setType(SpaceKTypeEnum.TACTIC_ATTACK.getType());
//			item.setCategory(baseItem.getId());
//			chart.getData().add(item);
//			KnowledgeChartLink link = new KnowledgeChartLink(baseItem.getId(),
//					category.getResourceId());
//			chart.getLinks().add(link);
//		}
		return chart;
    }

	/**
	 * 按 categoryID 列出
	 *
	 * @param ids id列表
	 * @return list<字符串>
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	@Override
	public List<String> listByCategoryids(List<String> ids) {
		return spaceTacticAttackMapper.idsByCategoryids(ids);
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return 空间战法攻击类别
	 * @throws
	 * @see TacticAttackCategoryAddReq
	 * @see SpaceTacticAttackCategory
	 */
	private SpaceTacticAttackCategory req2Entity(TacticAttackCategoryAddReq req) {
		SpaceTacticAttackCategory category = new SpaceTacticAttackCategory();
		category.setName(req.getName());
		category.setResourceId(IDGeneratorUtil.getID());
		return category;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return 空间战法攻击
	 * @throws
	 * @see AttackAddReq
	 * @see SpaceTacticAttack
	 */
	private SpaceTacticAttack req2Entity(AttackAddReq req) {
		SpaceTacticAttack attack = new SpaceTacticAttack();
		attack.setName(req.getName());
		attack.setCategoryId(req.getCategoryId());
		attack.setSource(req.getSource());
		attack.setSystemId(req.getSystemId());
		attack.setAction(JsonUtil.arrayToString(req.getAction()));
		attack.setTool(JsonUtil.arrayToString(req.getTool()));
		attack.setStrategy(JsonUtil.arrayToString(
				StreamApiUtils.map(req.getStrategy(), SpaceActionStrategy::getResourceId)));
		attack.setBuiltin(false);
		return attack;
	}

	/**
	 * 实体 2Bo
	 *
	 * @param attack 攻击
	 * @return 攻击详情
	 * @throws
	 * @see SpaceTacticAttack
	 * @see AttackDetail
	 */
	private AttackDetail entity2Bo(SpaceTacticAttack attack) {
		AttackDetail detail = BeanUtils.copy(attack, AttackDetail.class);
		detail.setAction(JsonUtil.toJsonObjectArray(attack.getAction(), TacticAction.class));

		SpaceTacticAttackCategory category = categoryMapper.selectById(attack.getCategoryId());
		if (category != null) {
			detail.setCategoryName(category.getName());
		}
//		SpaceTacticDefenseSystem spaceTacticDefenseSystem = systemMapper.selectById(attack.getSystemId());
//		if (spaceTacticDefenseSystem != null) {
//			detail.setSystem(spaceTacticDefenseSystem.getName());
//		}
		// tool
		List<String> toolIds = JsonUtil.toJsonObjectArray(attack.getTool(), String.class);
		detail.setTool(toolService.listTacticTool(toolIds));

		List<String> strategyIds = JsonUtil.toJsonObjectArray(attack.getStrategy(), String.class);
		detail.setStrategy(strategyService.convert2ActionStrategy(strategyIds));
		return detail;
	}

	/**
	 * 获取或例外
	 *
	 * @param resourceId 资源id
	 * @return 空间战法攻击
	 * @throws
	 * @see String
	 * @see SpaceTacticAttack
	 */
	private SpaceTacticAttack getOrException(String resourceId) {
		SpaceTacticAttack attack = mapper.selectById(resourceId);
		if (null == attack) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return attack;
	}
}
