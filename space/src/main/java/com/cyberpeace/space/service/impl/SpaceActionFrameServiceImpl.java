package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.*;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceActionFrameServiceImpl extends ServiceImpl<SpaceActionFrameMapper, SpaceActionFrame> implements SpaceActionFrameService {

	@Autowired
	private SpaceActionRelationService relationService;

	@Autowired
	private SpaceActionTargetService targetService;

	@Autowired
	private SpaceActionMethodService methodService;

	@Autowired
	private SpaceActionService actionService;

	@Override
	public Integer countByType(Integer code) {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrame::getType, code);
		return this.count(wrapper);
	}

	@Override
	public SpaceActionFrameLevel getFrameLevels(String resourceId) {
		SpaceActionFrameLevel level = new SpaceActionFrameLevel(resourceId, "");
		List<String> targetIds = relationService.getLowerIdsByUpperId(resourceId);
		if (targetIds.isEmpty()) {
			return level;
		}
		// target level
		Map<String, SpaceActionTarget> targetMap = StreamApiUtils.toMap(
				targetService.listByIds(targetIds), SpaceActionTarget::getResourceId, t -> t);
		for (String targetId : targetIds) {
			if (targetMap.containsKey(targetId)) {
				SpaceActionTarget target = targetMap.get(targetId);
				SpaceActionFrameLevel targetLevel = new SpaceActionFrameLevel(targetId,
						target.getName());
				level.getChildren().add(targetLevel);
			}
		}
		// method level
		List<SpaceActionRelation> targetMethodRelations = relationService.listByUpperIds(targetIds);
		Map<String, List<SpaceActionRelation>> targetMethodMap = StreamApiUtils
				.groupBy(targetMethodRelations, SpaceActionRelation::getUpperId);
		List<String> methodIds = StreamApiUtils.map(targetMethodRelations,
				SpaceActionRelation::getLowerId);
		if (methodIds.isEmpty()) {
			return level;
		}
		Map<String, SpaceActionMethod> methodMap = StreamApiUtils.toMap(
				methodService.listByIds(methodIds), SpaceActionMethod::getResourceId, m -> m);
		for (SpaceActionFrameLevel targetLevel : level.getChildren()) {
			List<SpaceActionRelation> relations = targetMethodMap.get(targetLevel.getResourceId());
			for (SpaceActionRelation relation : relations) {
				SpaceActionMethod method = methodMap.get(relation.getLowerId());
				if (null != method) {
					targetLevel.getChildren().add(
							new SpaceActionFrameLevel(method.getResourceId(), method.getName()));
				}
			}
		}
		// action level
		List<SpaceActionRelation> methodActionRelations = relationService.listByUpperIds(methodIds);
		Map<String, List<SpaceActionRelation>> methodActionMap = StreamApiUtils
				.groupBy(methodActionRelations, SpaceActionRelation::getUpperId);
		List<String> actionIds = StreamApiUtils.map(methodActionRelations,
				SpaceActionRelation::getLowerId);
		if (actionIds.isEmpty()) {
			return level;
		}
		Map<String, SpaceAction> actionMap = StreamApiUtils
				.toMap(actionService.listByIds(actionIds), SpaceAction::getResourceId, a -> a);
		for (SpaceActionFrameLevel targetLevel : level.getChildren()) {
			for (SpaceActionFrameLevel methodLevel : targetLevel.getChildren()) {
				List<SpaceActionRelation> relations = methodActionMap
						.get(methodLevel.getResourceId());
				for (SpaceActionRelation relation : relations) {
					SpaceAction action = actionMap.get(relation.getLowerId());
					if (null != action) {
						methodLevel.getChildren().add(new SpaceActionFrameLevel(
								action.getResourceId(), action.getName()));
					}
				}
			}
		}
		return level;
	}
}