
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.SpaceActionFrameApplication;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import com.cyberpeace.space.dao.entity.SpaceTacticAttack;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameOrderMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameOrderService;
import com.cyberpeace.space.service.context.frame.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

	/**
	 * 创建空间行动框架订单
	 *
	 * 此方法用于处理空间行动框架订单的创建请求它首先检查订单的发布状态，
	 * 然后将请求数据转换为订单实体，设置必要的属性，最后保存订单实体到数据库
	 *
	 * @param req 空间行动框架订单请求对象，包含创建订单所需的信息
	 */
	@Override
	public void create(SpaceActionFrameOrderReq req) {
	    // 检查订单的发布状态，确保订单可以被创建
	    this.checkRelease(req.getApplicationId(), req.getStepId(), req.getTacticId());

	    // 将请求对象转换为订单实体对象
	    SpaceActionFrameOrder entity = BeanUtils.copy(req, SpaceActionFrameOrder.class);

	    // 设置订单的步骤ID
	    entity.setStep(req.getStepId());

	    // 生成并设置订单的唯一资源ID
	    entity.setResourceId(IDGeneratorUtil.getID());

	    // 设置订单的初始状态为未完成
	    entity.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);

	    // 设置订单的发送者
	    entity.setSender(req.getSender());

	    // 保存订单实体到数据库
	    this.save(entity);
	}

	/**
	 * 检查给定的应用程序步骤和战术是否已经发布
	 * 此方法用于防止重复发布相同的应用程序步骤和战术组合
	 * 它通过查询数据库中是否存在具有相同应用程序ID、步骤和战术Id的记录来实现
	 * 如果找到匹配的记录，表明该组合已经发布，将抛出异常阻止重复发布
	 *
	 * @param applicationId 应用程序的唯一标识符
	 * @param step 应用程序的特定步骤
	 * @param tacticId 使用的战术的标识符
	 * @throws BusinessException 如果找到匹配的记录，表示该战术已经发布，则抛出此异常
	 */
	private void checkRelease(String applicationId, String step, String tacticId) {
	    // 创建一个Lambda查询包装器，用于构建查询条件
	    LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = new LambdaQueryWrapper<>();
	    // 设置查询条件：应用程序ID必须匹配
	    wrapper.eq(SpaceActionFrameOrder::getApplicationId, applicationId);
	    // 设置查询条件：步骤必须匹配
	    wrapper.eq(SpaceActionFrameOrder::getStep, step);
	    // 设置查询条件：战术Id必须匹配
	    wrapper.eq(SpaceActionFrameOrder::getTacticId, tacticId);
	    // 如果查询结果大于0，说明存在相同的记录，战术已经发布
	    if (this.count(wrapper) > 0) {
	        // 抛出异常，指示战术已经发布
	        throw new BusinessException(SpaceError.TACTIC_RELEASED);
	    }
	}

	/**
	 * 完成订单状态更新
	 * 当订单完成时调用此方法，以更新订单状态为已完成
	 *
	 * @param resourceId 订单的唯一标识符如果传入的标识符为空，将抛出异常
	 * @throws BusinessException 当 resourceId 为空时抛出此异常，表示操作失败
	 */
	@Override
	public void complete(String resourceId) {
	    // 检查 resourceId 是否为空，如果为空则抛出异常
	    if (StringUtils.isBlank(resourceId)) {
	        throw new BusinessException(ErrorCode.BLANK_ID);
	    }

	    // 通过 resourceId 从数据库中查询订单实体
	    SpaceActionFrameOrder entity = mapper.selectById(resourceId);
	    // 更新订单状态为已完成
	    entity.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
	    // 将更新后的订单实体保存回数据库
	    this.updateById(entity);
	}

	/**
	 * 列出用户的订单
	 * 根据用户的查询请求，列出符合要求的空间行动框架订单
	 * 如果指定了模式，将只列出与该模式匹配的战术相关的订单
	 *
	 * @param req 用户的查询请求，包含查询条件和模式
	 * @return 返回一个列表，包含符合查询条件的订单信息
	 */
	@Override
	public List<SpaceActionFrameOrderBo> listMyOrders(SpaceActionFrameOrderListReq req) {
	    // 将用户的查询请求转换为查询条件
	    LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = req.req2QueryWrapper();
	    List<SpaceActionFrameOrderBo> res = Lists.newArrayList();
	    Map<String, String> tacticNameMap = Maps.newHashMap();

	    // 如果用户指定了模式，则查询与该模式匹配的攻击和防御战术
	    if (StringUtils.isNotBlank(req.getPattern())) {
	        LambdaQueryWrapper<SpaceTacticAttack> attackLambdaQueryWrapper = new LambdaQueryWrapper<>();
	        attackLambdaQueryWrapper.like(SpaceTacticAttack::getName, req.getPattern());
	        List<SpaceTacticAttack> attackList = attackMapper.selectList(attackLambdaQueryWrapper);
	        for (SpaceTacticAttack attack : attackList) {
	            tacticNameMap.put(attack.getResourceId(), attack.getName());
	        }

	        LambdaQueryWrapper<SpaceTacticDefense> defenseLambdaQueryWrapper = new LambdaQueryWrapper<>();
	        defenseLambdaQueryWrapper.like(SpaceTacticDefense::getName, req.getPattern());
	        List<SpaceTacticDefense> defenseList = defenseMapper.selectList(defenseLambdaQueryWrapper);
	        for (SpaceTacticDefense defense : defenseList) {
	            tacticNameMap.put(defense.getResourceId(), defense.getName());
	        }

	        // 如果没有找到任何匹配的战术，则直接返回空列表
	        if (tacticNameMap.isEmpty()) {
	            return res;
	        } else {
	            wrapper.in(SpaceActionFrameOrder::getTacticId, tacticNameMap.keySet());
	        }
	    }

	    // 获取用户信息映射，用于后续填充订单信息
	    Map<String, String> userMap = applicationService.getUserMap(req.getRvb_id(),
	            SpaceCommonConstant.RvbUserGroup.STAFF);

	    // 根据查询条件获取订单列表
	    List<SpaceActionFrameOrder> orderList = mapper.selectList(wrapper);

	    // 如果没有指定模式，查询所有战术的名称
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

	    // 获取步骤名称映射
	    List<String> applicationIds = StreamApiUtils.map(orderList,
	            SpaceActionFrameOrder::getApplicationId);
	    Map<String, String> stepMap = this.getStepMapByApplicationIds(applicationIds);

	    // 填充订单信息并返回
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

	/**
	 * 根据给定的安排信息和应用ID删除已发布的记录
	 * 此方法旨在构建查询条件以精确匹配不应删除的记录，并通过这些条件从数据库中移除不符合条件的记录
	 *
	 * @param arrangeBo 包含步骤和策略信息的ActionFrameArrangeBo对象，用于构建查询条件
	 * @param applicationId 应用ID，用于指定要操作的应用
	 */
	@Override
	public void deleteReleased(ActionFrameArrangeBo arrangeBo, String applicationId) {
	    // 提取步骤ID列表，用于后续构建查询条件
	    List<String> stepIds = StreamApiUtils.map(arrangeBo.getSteps(),
	            ActionFrameStep::getResourceId);

	    // 初始化查询条件构建器
	    LambdaQueryWrapper<SpaceActionFrameOrder> queryWrapper = new LambdaQueryWrapper<>();
	    queryWrapper.eq(SpaceActionFrameOrder::getApplicationId, applicationId);

	    // 如果步骤ID列表不为空，则构建更复杂的查询条件
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

	    // 执行删除操作，根据构建的查询条件移除记录
	    this.remove(queryWrapper);
	}

	/**
	 * 根据应用ID列表获取步骤映射
	 * 该方法旨在通过一系列应用ID来检索并构建一个映射，该映射包含了每个应用中每个步骤的资源ID和名称
	 *
	 * @param applicationIds 应用ID列表，用于检索应用信息
	 * @return 返回一个映射，其中包含所有应用中每个步骤的资源ID和对应的名称
	 */
	private Map<String, String> getStepMapByApplicationIds(List<String> applicationIds) {
	    // 初始化一个空的HashMap，用于存储步骤的资源ID和名称
	    Map<String, String> res = Maps.newHashMap();

	    // 检查传入的应用ID列表是否为空，如果为空则直接返回空映射
	    if(applicationIds.isEmpty()){
	        return res;
	    }

	    // 调用应用服务，根据应用ID列表获取对应的应用信息列表
	    List<SpaceActionFrameApplication> applicationList = applicationService
	            .listByIds(applicationIds);

	    // 遍历应用信息列表
	    for (SpaceActionFrameApplication application : applicationList) {
	        // 获取当前应用的布局对象
	        ActionFrameArrangeBo arrangeBo = application.getArrangeBo();

	        // 遍历当前应用的所有步骤
	        for (ActionFrameStep step : arrangeBo.getSteps()) {
	            // 将步骤的资源ID和名称作为键值对存入映射中
	            res.put(step.getResourceId(), step.getName());
	        }
	    }

	    // 返回构建完成的映射
	    return res;
	}

}
