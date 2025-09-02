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
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.controller.res.SystemTreeRes;
import com.cyberpeace.space.controller.res.TacticDefenseRes;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseCategoryMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseSystemMapper;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartCategory;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;
import com.cyberpeace.space.service.context.index.KnowledgeChartLink;
import com.cyberpeace.space.service.context.tactic.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 空间策略防御Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see SpaceTacticDefenseService
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 该代码实现了一个基于 `MyBatis-Plus` 的服务类 `SpaceTacticDefenseServiceImpl`，主要用于处理与防御策略相关的业务逻辑。服务类继承自 `ServiceImpl`，并实现了 `SpaceTacticDefenseService` 接口。 
 * --- 
 * #### 主要功能模块 
 * 1. **分页查询** 
 * - 方法 `page(SpaceTacticPageReq req)`：根据请求参数 `req` 分页查询防御策略列表，并填充相关联的类别和系统信息。 
 * - 使用了 `LambdaQueryWrapper` 构建查询条件，并通过 `selectPage` 方法获取分页结果。 
 * 2. **数据转换** 
 * - 将数据库实体（如 `SpaceTacticDefense`）转换为业务对象（如 `DefenseListBo`、`DefenseDetail`），利用工具类 `BeanUtils` 和 `JsonUtil` 进行数据映射。 
 * 3. **增删改查** 
 * - **新增**：`add(DefenseAddReq req)` 和 `addDefenseCategory(TacticDefenseCategoryAddReq req)` 分别用于新增防御策略和防御类别。 
 * - **更新**：`updateDefense(DefenseModifyReq req)` 和 `updateDefenseCategory(TacticDefenseCategoryModifyReq req)` 用于更新防御策略和类别。 
 * - **删除**：`deleteBatch(List<String> ids)` 和 `deleteCategory(List<String> ids)` 批量删除防御策略和类别。 
 * 4. **详细信息获取** 
 * - `getDetail(String resourceId)` 和 `getCategoryDetail(String resourceId)` 分别用于获取防御策略和防御类别的详细信息。 
 * - 若资源不存在，则抛出 `BusinessException` 异常。 
 * 5. **分类管理** 
 * - 提供分页查询分类（`pageCategory(PageReqBase reqBase)`）、按 ID 查询分类（`listCategory()`）、新增分类（`addDefenseCategory`）、更新分类（`updateDefenseCategory`）以及删除分类（`deleteCategory`）等功能。 
 * 6. **框架与图表生成** 
 * - `getFrame()`：生成防御策略的动作框架层级结构。 
 * - `getTacticDefenseChart()`：生成防御策略的知识点图表，包含节点和链接信息。 
 * 7. **其他辅助方法** 
 * - `req2Entity`：将请求对象转换为数据库实体。 
 * - `entity2Bo`：将数据库实体转换为业务对象。 
 * - `getOrException` 和 `getCategoryOrException`：检查资源是否存在，若不存在则抛出异常。 
 * --- 
 * #### 技术亮点 
 * 1. **MyBatis-Plus 支持** 
 * - 使用了 `LambdaQueryWrapper` 和 `IPage` 提供了灵活且强大的查询能力。 
 * 2. **依赖注入** 
 * - 通过 `@Autowired` 注入多个 Mapper 和工具类（如 `UtilService`、`ToolService`），体现了良好的解耦设计。 
 * 3. **数据转换** 
 * - 利用 `BeanUtils` 和工具类（如 `JsonUtil` 和 `StreamApiUtils`）实现高效的实体与业务对象之间的转换。 
 * 4. **异常处理** 
 * - 在资源未找到时抛出自定义异常 `BusinessException`，便于统一处理错误逻辑。 
 * 5. **框架与图表生成** 
 * - 使用自定义的数据结构（如 `SpaceFrameLevel` 和 `KnowledgeChart`）生成复杂的框架和图表，支持可视化展示。 
 * --- 
 * #### 总结 
 * 该代码是一个典型的业务服务层实现，涵盖了防御策略的全生命周期管理（CRUD + 分页 + 图表生成）。代码结构清晰，功能模块划分明确，采用了主流的开发框架和技术手段，适合作为参考示例。 
**/
@Service
@Slf4j
public class SpaceTacticDefenseServiceImpl extends ServiceImpl<SpaceTacticDefenseMapper, SpaceTacticDefense> implements SpaceTacticDefenseService {

	/**
	 * 映射
	 */
	@Autowired
	private SpaceTacticDefenseMapper mapper;

	@Autowired
	private SpaceTacticDefenseSystemMapper systemMapper;

	@Autowired
	private ToolService toolService;

	@Autowired
	private SpaceStrategyService strategyService;

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

	@Autowired
	private SpaceTacticDefenseMapper spaceTacticDefenseMapper;

	/**
	 * 分页
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<DefenseListBo> }
	 * @throws
	 * @see SpaceTacticPageReq
	 * @see ListRsp<DefenseListBo>
	 */
	@Override
	public ListRsp<DefenseListBo> page(SpaceTacticPageReq req) {

		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceTacticDefense> wrapper = req.req2QueryWrapper();
		wrapper.eq(SpaceTacticDefense::getTmp, false);
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
	 * @throws
	 * @see DefenseAddReq
	 */
	@Override
	public void add(DefenseAddReq req, boolean tmp) {
		SpaceTacticDefense defense = this.req2Entity(req);
		defense.setResourceId(IDGeneratorUtil.getID());
		defense.setTmp(tmp);
		CrCommonValidator.validateNameLen(defense.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		mapper.insert(defense);
	}

	@Override
	public void tmpAdd(DefenseTmpAddReq req) {

		add(req, true);

		//todo 对接永信接口新增
	}

	/**
	 * 更新防御
	 *
	 * @param req 请求体
	 * @throws
	 * @see DefenseModifyReq
	 */
	@Override
	public void updateDefense(DefenseModifyReq req) {
		// todo id name
		SpaceTacticDefense defense = this.req2Entity(req);
		defense.setResourceId(req.getResourceId());
		CrCommonValidator.validateNameLen(defense.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		mapper.updateById(defense);
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
	 * @return {@code DefenseDetail }
	 * @throws
	 * @see String
	 * @see DefenseDetail
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
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<TacticDefenseCategoryBo>
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
	 * @throws
	 * @see TacticDefenseCategoryAddReq
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
	 * @throws
	 * @see TacticDefenseCategoryModifyReq
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
	 * @throws
	 * @see List<String>
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
	 * @throws
	 * @see String
	 * @see DefenseCategoryBo
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
	 * @throws
	 * @see List<TacticDefenseCategoryBo>
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
	 * @throws
	 * @see List<String>
	 * @see List<SpaceTacticDefense>
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
	 * @throws
	 * @see String
	 * @see SpaceTacticDefense
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
	 * @throws
	 * @see String
	 * @see List<DefenseDetail>
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
	 * @throws
	 * @see SpaceFrameLevel
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
	 * 获取战法防御图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
    public KnowledgeChart getTacticDefenseChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.TACTIC_DEFENSE,
				SpaceKTypeEnum.TACTIC.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.TACTIC_DEFENSE);
		chart.getCategories().add(baseCategory);
		for (SpaceTacticDefense defense : mapper.selectList(null)) {
			KnowledgeChartItem item = new KnowledgeChartItem(3);
			item.setId(defense.getResourceId());
			item.setName(defense.getName());
			item.setType(SpaceKTypeEnum.TACTIC_DEFENSE.getType());
			item.setCategory(baseItem.getId());
			chart.getData().add(item);
			KnowledgeChartLink link = new KnowledgeChartLink(baseItem.getId(),
					defense.getResourceId());
			chart.getLinks().add(link);
		}
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
		return spaceTacticDefenseMapper.idsByCategoryids(ids);
    }

    @Override
	public List<SystemTreeRes> tree(String systemId) {
		LambdaQueryWrapper<SpaceTacticDefenseSystem> systemWrapper = new LambdaQueryWrapper<>();
		systemWrapper.orderByDesc(SpaceTacticDefenseSystem::getCreateTime);
		LambdaQueryWrapper<SpaceTacticDefense> defenseWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotEmpty(systemId)) {
			systemWrapper.eq(SpaceTacticDefenseSystem::getResourceId, systemId);
			defenseWrapper.eq(SpaceTacticDefense::getSystemId, systemId);
		}
		List<SpaceTacticDefenseSystem> systemList = systemMapper.selectList(systemWrapper);
		List<SpaceTacticDefense> defenseList = spaceTacticDefenseMapper.selectList(defenseWrapper);
		Map<String, List<SpaceTacticDefense>> defenseMap = defenseList.stream()
				.collect(Collectors.groupingBy(
						SpaceTacticDefense::getSystemId,
						Collectors.toList()
				));
		List<SystemTreeRes> treeResList = new ArrayList<>();
		for (SpaceTacticDefenseSystem system: systemList) {
			SystemTreeRes treeRes = new SystemTreeRes();
			treeRes.setSystemId(system.getResourceId());
			treeRes.setName(system.getName());
			List<SpaceTacticDefense> defenses = defenseMap.getOrDefault(system.getResourceId(), new ArrayList<>());
			List<TacticDefenseRes> defenseResList = new ArrayList<>();
			for (SpaceTacticDefense defense : defenses) {
				TacticDefenseRes defenseRes = new TacticDefenseRes();
				defenseRes.setName(defense.getName());
				defenseRes.setDefenseId(defense.getResourceId());
				defenseResList.add(defenseRes);
			}
			treeRes.setTacticDefenseList(defenseResList);
			treeResList.add(treeRes);
		}
		return treeResList;
	}

	/**
	 * 获取阶段框架
	 *
	 * @param stage       阶段
	 * @param stageName   阶段名称
	 * @param defenseMap  防御地图
	 * @param categoryBos 类别 BOS
	 * @return {@code SpaceFrameLevel }
	 * @throws
	 * @see Integer
	 * @see String
	 * @see Map<String, List<SpaceTacticDefense>>
	 * @see List<TacticDefenseCategoryBo>
	 * @see SpaceFrameLevel
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
	 * @throws
	 * @see TacticDefenseCategoryAddReq
	 * @see SpaceTacticDefenseCategory
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
	 * @throws
	 * @see DefenseAddReq
	 * @see SpaceTacticDefense
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
		defense.setStrategy(JsonUtil.arrayToString(
				StreamApiUtils.map(req.getStrategy(), SpaceActionStrategy::getResourceId)));
		defense.setBuiltin(false);
		return defense;
	}

	/**
	 * 实体 2 bo
	 *
	 * @param defense 防御
	 * @return {@code DefenseDetail }
	 * @throws
	 * @see SpaceTacticDefense
	 * @see DefenseDetail
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

		List<String> strategyIds = JsonUtil.toJsonObjectArray(defense.getStrategy(), String.class);
		detail.setStrategy(strategyService.convert2ActionStrategy(strategyIds));

		return detail;
	}

	/**
	 * 获取或例外
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceTacticDefense }
	 * @throws
	 * @see String
	 * @see SpaceTacticDefense
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
	 * @throws
	 * @see String
	 * @see SpaceTacticDefenseCategory
	 */
	private SpaceTacticDefenseCategory getCategoryOrException(String resourceId) {
		SpaceTacticDefenseCategory category = categoryMapper.selectById(resourceId);
		if (null == category) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

		}
		return category;
	}
}

