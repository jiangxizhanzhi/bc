package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameOrderMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseSystemMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameOrderService;
import com.cyberpeace.space.service.context.frame.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ### 代码分析与总结 
 * #### 概述 
 * 该代码是一个基于 `MyBatis-Plus` 的服务实现类 `SpaceActionFrameOrderServiceImpl`，实现了 `SpaceActionFrameOrderService` 接口。主要功能是处理与 `SpaceActionFrameOrder` 相关的操作，包括创建、完成、查询和删除订单。 
 * #### 主要功能模块 
 * 1. **创建订单** 
 * - 方法：`create(SpaceActionFrameOrderReq req)` 
 * - 功能：检查是否已存在相同的订单（通过调用 `checkRelease` 方法），生成新的订单实体并保存到数据库。 
 * - 使用工具： 
 * - `BeanUtils.copy`：将请求对象转换为实体对象。 
 * - `IDGeneratorUtil.getID`：生成唯一的资源 ID。 
 * - `SpaceCommonConstant.OrderStatus.UNFINISHED`：设置订单状态为未完成。 
 * 2. **检查订单是否已发布** 
 * - 方法：`private void checkRelease(String applicationId, String step, String tacticId)` 
 * - 功能：通过 `LambdaQueryWrapper` 查询是否有重复的订单，并抛出异常 `BusinessException` 如果存在。 
 * 3. **完成订单** 
 * - 方法：`complete(String resourceId)` 
 * - 功能：更新指定资源 ID 的订单状态为已完成。 
 * - 使用工具： 
 * - `mapper.selectById(resourceId)`：根据资源 ID 查询订单。 
 * - `updateById(entity)`：更新订单状态。 
 * 4. **查询我的订单** 
 * - 方法：`listMyOrders(SpaceActionFrameOrderListReq req)` 
 * - 功能：根据条件查询用户的所有订单，并返回带扩展信息的订单列表。 
 * - 关键步骤： 
 * - 构建查询条件（`req.req2QueryWrapper()`）。 
 * - 处理策略名称映射（`tacticNameMap`）。 
 * - 获取步骤名称映射（`stepMap`）。 
 * - 将订单实体转换为业务对象（`SpaceActionFrameOrderBo`）。 
 * 5. **删除已发布的订单** 
 * - 方法：`deleteReleased(ActionFrameArrangeBo arrangeBo, String applicationId)` 
 * - 功能：删除不符合当前步骤或策略的已发布订单。 
 * - 使用工具： 
 * - `StreamApiUtils.map`：将集合中的元素映射为其他形式。 
 * - `LambdaQueryWrapper`：构建复杂的查询条件。 
 * 6. **获取步骤名称映射** 
 * - 方法：`private Map<String, String> getStepMapByApplicationIds(List<String> applicationIds)` 
 * - 功能：根据应用 ID 列表获取步骤名称映射。 
 * #### 技术亮点 
 * - **MyBatis-Plus**：使用 `LambdaQueryWrapper` 构建动态 SQL 查询，提高了代码的可读性和安全性。 
 * - **Guava 和 Apache Commons**：利用 `Maps.newHashMap` 和 `Lists.newArrayList` 提供高效的集合操作。 
 * - **Lombok**：通过 `@Slf4j` 注解简化日志记录，减少冗余代码。 
 * - **分层设计**：通过 `applicationService` 调用其他服务，体现了良好的模块化设计。 
 * #### 总结 
 * 该代码实现了订单管理的核心功能，包括创建、完成、查询和删除订单。通过合理的工具选择和分层设计，代码具有较高的可维护性和扩展性。 
**/

@Service
@Slf4j
public class SpaceActionFrameOrderServiceImpl extends ServiceImpl<SpaceActionFrameOrderMapper, SpaceActionFrameOrder> implements SpaceActionFrameOrderService {

	@Autowired
	private SpaceActionFrameOrderMapper mapper;

	@Autowired
	private SpaceTacticAttackMapper attackMapper;

	@Autowired
	private SpaceTacticDefenseMapper defenseMapper;

	@Autowired
	private SpaceActionFrameApplicationService applicationService;

	@Autowired
	private SpaceTacticDefenseSystemMapper defenseSystemMapper;

	@Autowired
	private CrConstantsService crConstantsService;

	@Override
	public void create(SpaceActionFrameOrderReq req) {
		this.checkRelease(req.getApplicationId(), req.getStepId(), req.getTacticId());
		SpaceActionFrameOrder entity = BeanUtils.copy(req, SpaceActionFrameOrder.class);
		entity.setStep(req.getStepId());
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
		entity.setSender(req.getSender());
		this.save(entity);
	}

	private void checkRelease(String applicationId, String step, String tacticId) {
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameOrder::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameOrder::getStep, step);
		wrapper.eq(SpaceActionFrameOrder::getTacticId, tacticId);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.TACTIC_RELEASED);
		}
	}

	@Override
	public void complete(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		SpaceActionFrameOrder entity = mapper.selectById(resourceId);
		entity.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
		this.updateById(entity);
	}

	@Override
	public List<SpaceActionFrameOrderBo> listMyOrders(SpaceActionFrameOrderListReq req) {
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = req.req2QueryWrapper();
		List<SpaceActionFrameOrderBo> res = Lists.newArrayList();
		Map<String, String> tacticNameMap = Maps.newHashMap();
		if (StringUtils.isNotBlank(req.getPattern())) {
			LambdaQueryWrapper<SpaceTacticAttack> attackLambdaQueryWrapper = new LambdaQueryWrapper<>();
			attackLambdaQueryWrapper.like(SpaceTacticAttack::getName, req.getPattern());
			List<SpaceTacticAttack> attackList = attackMapper.selectList(attackLambdaQueryWrapper);
			for (SpaceTacticAttack attack : attackList) {
				tacticNameMap.put(attack.getResourceId(), attack.getName());
			}
			LambdaQueryWrapper<SpaceTacticDefense> defenseLambdaQueryWrapper = new LambdaQueryWrapper<>();
			defenseLambdaQueryWrapper.like(SpaceTacticDefense::getName, req.getPattern());
			List<SpaceTacticDefense> defenseList = defenseMapper
					.selectList(defenseLambdaQueryWrapper);
			for (SpaceTacticDefense defense : defenseList) {
				tacticNameMap.put(defense.getResourceId(), defense.getName());
			}
			if (tacticNameMap.isEmpty()) {
				return res;
			} else {
				wrapper.in(SpaceActionFrameOrder::getTacticId, tacticNameMap.keySet());
			}
		}
		Map<String, String> userMap = applicationService.getUserMap(req.getRvb_id(),
				SpaceCommonConstant.RvbUserGroup.STAFF);
		List<SpaceActionFrameOrder> orderList = mapper.selectList(wrapper);
		List<String> tacticIds = StreamApiUtils.map(orderList, SpaceActionFrameOrder::getTacticId);
		if (StringUtils.isBlank(req.getPattern()) && !tacticIds.isEmpty()) {
			List<SpaceTacticAttack> attackList = attackMapper.selectBatchIds(tacticIds);
			for (SpaceTacticAttack attack : attackList) {
				tacticNameMap.put(attack.getResourceId(), attack.getName());
			}
			List<SpaceTacticDefense> defenseList = defenseMapper.selectBatchIds(tacticIds);
			for (SpaceTacticDefense defense : defenseList) {
				tacticNameMap.put(defense.getResourceId(), defense.getName());
			}
		}
		// get step name
		List<String> applicationIds = StreamApiUtils.map(orderList,
				SpaceActionFrameOrder::getApplicationId);
		Map<String, String> stepMap = this.getStepMapByApplicationIds(applicationIds);

		for (SpaceActionFrameOrder order : orderList) {
			SpaceActionFrameOrderBo bo = BeanUtils.copy(order, SpaceActionFrameOrderBo.class);
			if (stepMap.containsKey(order.getStep())) {
				bo.setStepId(order.getStep());
				bo.setStepName(stepMap.get(order.getStep()));
			} else {
				continue;
			}
			bo.setReleased(true);
			bo.setTacticName(tacticNameMap.get(order.getTacticId()));
			bo.setSenderName(userMap.get(order.getSender()));
			res.add(bo);
		}
		return res;
	}

	@Override
	public void deleteReleased(ActionFrameArrangeBo arrangeBo, String applicationId) {
		List<String> stepIds = StreamApiUtils.map(arrangeBo.getSteps(),
				ActionFrameStep::getResourceId);
		LambdaQueryWrapper<SpaceActionFrameOrder> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SpaceActionFrameOrder::getApplicationId, applicationId);
		if (!stepIds.isEmpty()) {
			queryWrapper.and(wrapper -> {
				wrapper.notIn(SpaceActionFrameOrder::getStep, stepIds);
				for (ActionFrameStep step : arrangeBo.getSteps()) {
					List<String> tacticIds = StreamApiUtils.map(step.getTactics(),
							ActionFrameTactic::getResourceId);
					if (!tacticIds.isEmpty()) {
						wrapper.or(wrapperTactic -> wrapperTactic
								.eq(SpaceActionFrameOrder::getStep, step.getResourceId())
								.notIn(SpaceActionFrameOrder::getTacticId, tacticIds));
					} else {
						wrapper.or(wrapperTactic -> wrapperTactic
								.eq(SpaceActionFrameOrder::getStep, step.getResourceId()));
					}
				}
			});
		}

		this.remove(queryWrapper);
	}

	@Override
	public void report(SpaceActionFrameOrderReportReq reportReq) {
		SpaceActionFrameOrder entity = BeanUtils.copy(reportReq, SpaceActionFrameOrder.class);
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
		this.save(entity);
	}

	@Override
	public List<SpaceActionFrameOrderPBBo> listMyPBOrders(SpaceActionFrameOrderPBListReq req) {

		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = req.req2QueryWrapper();
		List<SpaceActionFrameOrderPBBo> res = Lists.newArrayList();
		Map<String, String> tacticNameMap = Maps.newHashMap();
//		if (StringUtils.isNotBlank(req.getPattern())) {
//			LambdaQueryWrapper<SpaceTacticAttack> attackLambdaQueryWrapper = new LambdaQueryWrapper<>();
//			attackLambdaQueryWrapper.like(SpaceTacticAttack::getName, req.getPattern());
//			List<SpaceTacticAttack> attackList = attackMapper.selectList(attackLambdaQueryWrapper);
//			for (SpaceTacticAttack attack : attackList) {
//				tacticNameMap.put(attack.getResourceId(), attack.getName());
//			}
//			LambdaQueryWrapper<SpaceTacticDefense> defenseLambdaQueryWrapper = new LambdaQueryWrapper<>();
//			defenseLambdaQueryWrapper.like(SpaceTacticDefense::getName, req.getPattern());
//			List<SpaceTacticDefense> defenseList = defenseMapper
//					.selectList(defenseLambdaQueryWrapper);
//			for (SpaceTacticDefense defense : defenseList) {
//				tacticNameMap.put(defense.getResourceId(), defense.getName());
//			}
//			if (tacticNameMap.isEmpty()) {
//				return res;
//			} else {
//				wrapper.in(SpaceActionFrameOrder::getTacticId, tacticNameMap.keySet());
//			}
//		}
//		Map<String, String> userMap = applicationService.getUserMap(req.getRvb_id(),
//				SpaceCommonConstant.RvbUserGroup.STAFF);
		List<SpaceActionFrameOrder> orderList = mapper.selectList(wrapper);
		List<String> tacticIds = StreamApiUtils.map(orderList, SpaceActionFrameOrder::getTacticId);

		LambdaQueryWrapper<SpaceTacticDefenseSystem> defenseSystemWrapper = new LambdaQueryWrapper<>();
		Map<String, SpaceTacticDefense> defenseMap = new HashMap<>();

		if (!tacticIds.isEmpty()) {
			List<SpaceTacticAttack> attackList = attackMapper.selectBatchIds(tacticIds);
			for (SpaceTacticAttack attack : attackList) {
				tacticNameMap.put(attack.getResourceId(), attack.getName());
			}

			List<SpaceTacticDefense> defenseList = defenseMapper.selectBatchIds(tacticIds);
			defenseMap = defenseList.stream().collect(Collectors.toMap(SpaceTacticDefense::getResourceId, d -> d));
			for (SpaceTacticDefense defense : defenseList) {
				tacticNameMap.put(defense.getResourceId(), defense.getName());
			}
			List<String> defenseSystemIds = defenseList.stream().map(SpaceTacticDefense::getSystemId).collect(Collectors.toList());
			defenseSystemWrapper.in(SpaceTacticDefenseSystem::getResourceId, defenseSystemIds);
		}

		List<SpaceTacticDefenseSystem> systemList = defenseSystemMapper.selectList(defenseSystemWrapper);
		Map<String, SpaceTacticDefenseSystem> defenseSystemMap = systemList.stream().collect(Collectors.toMap(SpaceTacticDefenseSystem::getResourceId, s -> s));

		List<CrConstantsConfig> constants = crConstantsService.getByType(48);
		Map<Integer, String> constantMap = constants.stream().collect(Collectors.toMap(CrConstantsConfig::getCode, CrConstantsConfig::getCnName));


//		List<String> tacticIds = orderList.stream().map(SpaceActionFrameOrder::getTacticId).collect(Collectors.toList());
		// get step name
//		List<String> applicationIds = StreamApiUtils.map(orderList,
//				SpaceActionFrameOrder::getApplicationId);
//		Map<String, String> stepMap = this.getStepMapByApplicationIds(applicationIds);

		for (SpaceActionFrameOrder order : orderList) {
//			SpaceActionFrameOrderBo bo = BeanUtils.copy(order, SpaceActionFrameOrderBo.class);
			SpaceActionFrameOrderPBBo bo = BeanUtils.copy(order, SpaceActionFrameOrderPBBo.class);
			bo.setName(tacticNameMap.get(order.getTacticId()));
			bo.setTime(order.getCreateTime());

//			if (stepMap.containsKey(order.getStep())) {
//				bo.setStepId(order.getStep());
//				bo.setStepName(stepMap.get(order.getStep()));
//			} else {
//				continue;
//			}
			SpaceTacticDefense defense = defenseMap.get(order.getTacticId());
			if (defense != null) {
				SpaceTacticDefenseSystem system = defenseSystemMap.get(defense.getSystemId());
				bo.setTacticName(system.getName());
				bo.setTacticId(defense.getResourceId());

				bo.setActionCount(JsonUtil.stringToArray(defense.getAction()).size());
				bo.setToolCount(JsonUtil.toJsonObjectArray(defense.getTool(), String.class).size());
				bo.setStep(constantMap.get(system.getStage()));
			}

			res.add(bo);
		}
		return res;
	}

	private Map<String, String> getStepMapByApplicationIds(List<String> applicationIds) {
		Map<String, String> res = Maps.newHashMap();
		if(applicationIds.isEmpty()){
			return res;
		}
		List<SpaceActionFrameApplication> applicationList = applicationService
				.listByIds(applicationIds);
		for (SpaceActionFrameApplication application : applicationList) {
			ActionFrameArrangeBo arrangeBo = application.getArrangeBo();
			for (ActionFrameStep step : arrangeBo.getSteps()) {
				res.put(step.getResourceId(), step.getName());
			}
		}
		return res;
	}

}

