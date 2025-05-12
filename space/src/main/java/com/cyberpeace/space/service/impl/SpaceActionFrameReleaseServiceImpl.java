package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.*;
import com.cyberpeace.space.service.SpaceActionFrameApplicationService;
import com.cyberpeace.space.service.SpaceActionFrameReleaseService;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.context.frame.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class SpaceActionFrameReleaseServiceImpl extends ServiceImpl<SpaceActionFrameReleaseMapper, SpaceActionFrameRelease> implements SpaceActionFrameReleaseService {

	@Autowired
	private SpaceActionFrameApplicationMapper applicationMapper;

	@Autowired
	private SpaceTacticDefenseMapper tacticDefenseMapper;

	@Autowired
	private SpaceTacticAttackMapper tacticAttackMapper;

	@Autowired
	private SpaceActionFrameOrderMapper orderMapper;

	@Autowired
	private SpaceActionFrameApplicationService applicationService;

	@Autowired
	private SpaceActionFrameService frameService;

	@Override
	public void create(SpaceActionFrameReleaseReq req) {
		this.checkRelease(req.getApplicationId(), req.getStep());
		SpaceActionFrameRelease entity = BeanUtils.copy(req, SpaceActionFrameRelease.class);
		SpaceActionFrameApplication application = applicationMapper
				.selectById(req.getApplicationId());
		entity.setFrameName(application.getName());
		entity.setRvbId(application.getRvbId());

		ActionFrameArrangeBo frame = application.getArrangeBo();
		for (ActionFrameStep step : frame.getSteps()) {
			if (req.getStep().equals(step.getName())) {
				entity.setTactics(JsonUtil.arrayToString(step.getTactics()));
			}
		}
		this.save(entity);
	}

	@Override
	public List<SpaceActionFrameOrderBo> getTactics(String applicationId) {
		List<SpaceActionFrameOrderBo> res = Lists.newArrayList();
		SpaceActionFrameApplication application = applicationMapper.selectById(applicationId);
		if(null == application){
			return res;
		}
		ActionFrameArrangeBo actionFrameArrangeBo = application.getArrangeBo();

		// order complete stats
		LambdaQueryWrapper<SpaceActionFrameOrder> orderWrapper = new LambdaQueryWrapper<>();
		orderWrapper.eq(SpaceActionFrameOrder::getApplicationId, application.getResourceId());
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils
				.groupBy(orderMapper.selectList(orderWrapper), SpaceActionFrameOrder::getStep);
		Map<String, String> userMap = applicationService.getUserMap(application.getRvbId(),
				SpaceCommonConstant.RvbUserGroup.WORKER);
		Map<String, String> tacticNameMap = frameService.getTacticNameMap(actionFrameArrangeBo);
		
		// keep order of step & tactic
		for (ActionFrameStep step : actionFrameArrangeBo.getSteps()) {
			List<SpaceActionFrameOrder> orderList = orderMap.getOrDefault(step.getResourceId(),
					Lists.newArrayList());
			Map<String, SpaceActionFrameOrder> map = StreamApiUtils.toMap(orderList,
					SpaceActionFrameOrder::getTacticId, a -> a);
			for (ActionFrameTactic tactic : step.getTactics()) {
				SpaceActionFrameOrderBo bo = new SpaceActionFrameOrderBo();
				bo.setFrameName(application.getName());
				bo.setTacticName(tacticNameMap.containsKey(tactic.getResourceId())? tacticNameMap.get(tactic.getResourceId()):tactic.getName());
				bo.setTacticId(tactic.getResourceId());
				bo.setTacticType(tactic.getType());
				bo.setStepName(step.getName());
				bo.setStepId(step.getResourceId());
				bo.setApplicationId(applicationId);
				if(map.containsKey(tactic.getResourceId())){
					bo.setResourceId(map.get(tactic.getResourceId()).getResourceId());
					bo.setUserId(map.get(tactic.getResourceId()).getUserId());
					bo.setUserName(userMap.get(bo.getUserId()));
					bo.setReleased(true);
					bo.setStatus(map.get(tactic.getResourceId()).getStatus());
				}
				res.add(bo);
			}
		}
		return res;
	}

	@Override
	public SpaceActionFrameApplicationBo getApplicationBo(String resourceId) {
		SpaceActionFrameApplication application = applicationService.getById(resourceId);
		if (null == application) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SpaceActionFrameApplicationBo bo = application.entity2Bo(Maps.newHashMap());
		// 重置进度
		bo.getFrame().resetStatus();

//		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
//		wrapper.eq(SpaceActionFrameRelease::getApplicationId, application.getResourceId());
//		List<SpaceActionFrameRelease> releaseList = this.list(wrapper);
//		Map<String, SpaceActionFrameRelease> releaseMap = StreamApiUtils.toMap(releaseList,
//				SpaceActionFrameRelease::getStep, a -> a);
		
		// order complete status
		LambdaQueryWrapper<SpaceActionFrameOrder> orderWrapper = new LambdaQueryWrapper<>();
		orderWrapper.eq(SpaceActionFrameOrder::getApplicationId, application.getResourceId());
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils
				.groupBy(orderMapper.selectList(orderWrapper), SpaceActionFrameOrder::getStep);
		Map<String, String> tacticNameMap = frameService.getTacticNameMap(bo.getFrame());
		for (ActionFrameStep step : bo.getFrame().getSteps()) {
			if (!orderMap.containsKey(step.getResourceId())) {
//				if (releaseMap.containsKey(step.getName())) {
//					step.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
//				}
				continue;
			}
			int complete = 0;
			List<SpaceActionFrameOrder> orderList = orderMap.get(step.getResourceId());
			Map<String, SpaceActionFrameOrder> map = StreamApiUtils.toMap(orderList,
					SpaceActionFrameOrder::getTacticId, o -> o);
			for (ActionFrameTactic tactic : step.getTactics()) {
				if (map.containsKey(tactic.getResourceId())) {
					SpaceActionFrameOrder order = map.get(tactic.getResourceId());
					tactic.setStatus(order.getStatus());
					if (Objects.equals(order.getStatus(),
							SpaceCommonConstant.OrderStatus.FINISHED)) {
						complete++;
					}
				}
				if (tacticNameMap.containsKey(tactic.getResourceId())) {
					tactic.setName(tacticNameMap.get(tactic.getResourceId()));
				}
			}
			if (complete == step.getTactics().size()) {
				step.setStatus(SpaceCommonConstant.OrderStatus.FINISHED);
			} else {
				step.setStatus(SpaceCommonConstant.OrderStatus.UNFINISHED);
			}
		}
		return bo;

	}

	public void checkRelease(String applicationId, String stage) {
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		wrapper.eq(SpaceActionFrameRelease::getStep, stage);
		if (this.count(wrapper) > 0) {
			throw new BusinessException(SpaceError.STAGE_RELEASED);
		}
	}
}
