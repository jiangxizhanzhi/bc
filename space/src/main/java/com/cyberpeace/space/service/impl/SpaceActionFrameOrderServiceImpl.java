
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

/**
 * 空间行动框架次序Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionFrameOrderService
 * @since 1.0.0
 */
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
	 * 创建
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceActionFrameOrderReq
	 */
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

	/**
	 * 检查下发
	 *
	 * @param applicationId 行为依赖id
	 * @param step          步骤
	 * @param tacticId      战法id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	private void checkRelease(String applicationId, String step, String tacticId) {
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameOrder::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameOrder::getStep, step);
		wrapper.eq(SpaceActionFrameOrder::getTacticId, tacticId);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.TACTIC_RELEASED);
		}
	}

	/**
	 * 完成
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	@Override
	public void complete(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		SpaceActionFrameOrder entity = mapper.selectById(resourceId);
		entity.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
		this.updateById(entity);
	}

	/**
	 * 列表我的订单
	 *
	 * @param req 请求体
	 * @return {@code List<SpaceActionFrameOrderBo> }
	 * @throws
	 * @see SpaceActionFrameOrderListReq
	 * @see List<SpaceActionFrameOrderBo>
	 */
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

	/**
	 * 删除已下发
	 *
	 * @param arrangeBo     安排Bo
	 * @param applicationId 行为依赖id
	 * @throws
	 * @see ActionFrameArrangeBo
	 * @see String
	 */
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

	/**
	 * 获取步骤映射由行为依赖id列表
	 *
	 * @param applicationIds 行为依赖id列表
	 * @return {@code Map<String, String> }
	 * @throws
	 * @see List<String>
	 * @see Map<String, String>
	 */
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
