package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.cyberpeace.space.service.context.frame.*;
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

	@Override
	public void create(SpaceActionFrameReleaseReq req) {
		this.checkRelease(req.getApplicationId(), req.getStep());
		SpaceActionFrameRelease entity = BeanUtils.copy(req, SpaceActionFrameRelease.class);
		SpaceActionFrameApplication application = applicationMapper
				.selectById(req.getApplicationId());
		entity.setFrameName(application.getName());
		entity.setRvbId(application.getRvbId());

		ActionFrameArrangeBo frame = JsonUtil.toJsonObject(application.getFrame(),
				ActionFrameArrangeBo.class);
		for (ActionFrameStep step : frame.getSteps()) {
			if (req.getStep().equals(step.getName())) {
				entity.setTactics(JsonUtil.arrayToString(step.getTactics()));
			}
		}
		this.save(entity);
	}

	@Override
	public List<SpaceActionFrameReleaseBo> listBo(String applicationId) {
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrameRelease::getApplicationId, applicationId);
		List<SpaceActionFrameRelease> releaseList = this.list(wrapper);
		Map<String, SpaceActionFrameRelease> releaseMap = StreamApiUtils.toMap(releaseList,
				SpaceActionFrameRelease::getStep, a -> a);
		SpaceActionFrameApplication application = applicationMapper.selectById(applicationId);

		List<SpaceActionFrameReleaseBo> res = Lists.newArrayList();
		ActionFrameArrangeBo actionFrameArrangeBo = JsonUtil.toJsonObject(application.getFrame(),
				ActionFrameArrangeBo.class);

		// order complete stats
		LambdaQueryWrapper<SpaceActionFrameOrder> orderWrapper = new LambdaQueryWrapper<>();
		orderWrapper.eq(SpaceActionFrameOrder::getRvbId, application.getRvbId());
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils
				.groupBy(orderMapper.selectList(orderWrapper), SpaceActionFrameOrder::getStep);
		Map<String, String> userMap = applicationService.getUserMap(application.getRvbId(),
				SpaceCommonConstant.RvbUserGroup.MIDDLE);
		for (ActionFrameStep step : actionFrameArrangeBo.getSteps()) {
			SpaceActionFrameReleaseBo bo = new SpaceActionFrameReleaseBo();
			bo.setApplicationId(applicationId);
			bo.setFrameName(application.getName());
			bo.setStep(step.getName());
			int complete = 0;
			if (releaseMap.containsKey(step.getName())) {
				bo.setUserId(releaseMap.get(step.getName()).getUserId());
				bo.setUserName(userMap.get(bo.getUserId()));
				List<SpaceActionFrameOrder> orderList = orderMap.getOrDefault(step.getName(),
						Lists.newArrayList());
				for (SpaceActionFrameOrder order : orderList) {
					if (Objects.equals(order.getStatus(),
							SpaceCommonConstant.OrderStatus.FINISHED)) {
						complete++;
					}
				}
				bo.setReleased(true);
			}
			int tacticCount = step.getTactics().size();
			bo.setProgress(complete + "/" + tacticCount);
			res.add(bo);
		}
		return res;

	}

	@Override
	public List<SpaceActionFrameOrderBo> getMySteps(String rvbId, String userId) {
		List<SpaceActionFrameOrderBo> res = Lists.newArrayList();
		LambdaQueryWrapper<SpaceActionFrameRelease> wrapperRelease = new LambdaQueryWrapper<>();
		wrapperRelease.eq(SpaceActionFrameRelease::getRvbId, rvbId);
		wrapperRelease.eq(SpaceActionFrameRelease::getUserId, userId);
		List<SpaceActionFrameRelease> releaseList = this.list(wrapperRelease);
		if (releaseList.isEmpty()) {
			return res;
		}
		LambdaQueryWrapper<SpaceActionFrameOrder> wrapperOrder = new LambdaQueryWrapper<>();
		wrapperOrder.eq(SpaceActionFrameOrder::getRvbId, rvbId);
		wrapperOrder.in(SpaceActionFrameOrder::getStep,
				StreamApiUtils.map(releaseList, SpaceActionFrameRelease::getStep));
		List<SpaceActionFrameOrder> orderList = orderMapper.selectList(wrapperOrder);
		Map<String, List<SpaceActionFrameOrder>> orderMap = StreamApiUtils.groupBy(orderList,
				SpaceActionFrameOrder::getStep);
		List<String> tacticIds = Lists.newArrayList();
		Map<String, String> userMap = applicationService.getUserMap(rvbId,
				SpaceCommonConstant.RvbUserGroup.WORKER);
		for (SpaceActionFrameRelease release : releaseList) {
			List<ActionFrameTactic> tactics = JsonUtil.toJsonObjectArray(release.getTactics(),
					ActionFrameTactic.class);
			for (ActionFrameTactic tactic : tactics) {
				SpaceActionFrameOrderBo bo = new SpaceActionFrameOrderBo();
				bo.setApplicationId(release.getApplicationId());
				bo.setFrameName(release.getFrameName());
				bo.setStep(release.getStep());
				bo.setTacticId(tactic.getResourceId());
				tacticIds.add(tactic.getResourceId());
				bo.setTacticType(tactic.getType());

				if (orderMap.containsKey(release.getStep())) {
					for (SpaceActionFrameOrder order : orderMap.get(release.getStep())) {
						if (tactic.getResourceId().equals(order.getTacticId())) {
							bo.setStatus(order.getStatus());
							bo.setUserId(order.getUserId());
							bo.setReleased(true);
							bo.setUserName(userMap.get(order.getUserId()));
							break;
						}
					}
				}

				res.add(bo);
			}
		}
		List<SpaceTacticAttack> attackList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticAttackMapper.selectBatchIds(tacticIds);
		List<SpaceTacticDefense> defenseList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticDefenseMapper.selectBatchIds(tacticIds);
		Map<String, SpaceTacticAttack> attackMap = StreamApiUtils.toMap(attackList,
				SpaceTacticAttack::getResourceId, t -> t);
		Map<String, SpaceTacticDefense> defenseMap = StreamApiUtils.toMap(defenseList,
				SpaceTacticDefense::getResourceId, t -> t);

		for (SpaceActionFrameOrderBo bo : res) {
			if (SpaceCommonConstant.TacticType.ATTACK.equals(bo.getTacticType())) {
				if (attackMap.containsKey(bo.getTacticId())) {
					bo.setTacticName(attackMap.get(bo.getTacticId()).getName());
				}
			} else if (SpaceCommonConstant.TacticType.DEFENSE.equals(bo.getTacticType())) {
				if (defenseMap.containsKey(bo.getTacticId())) {
					bo.setTacticName(defenseMap.get(bo.getTacticId()).getName());
				}
			}
		}

		return res;
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
