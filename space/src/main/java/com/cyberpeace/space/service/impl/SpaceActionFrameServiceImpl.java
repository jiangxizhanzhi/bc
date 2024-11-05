package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.*;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

	@Autowired
	private TransactionTemplate transactionTemplate;

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
			List<SpaceActionRelation> relations = targetMethodMap
					.getOrDefault(targetLevel.getResourceId(), Lists.newArrayList());
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
						.getOrDefault(methodLevel.getResourceId(), Lists.newArrayList());
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

	/**
	 * 保存帧
	 *
	 * @param req 请求体
	 */
	@Override
	public void saveFrame(SpaceActionFrameLevel req) {
		List<String> upperIds = Lists.newArrayList();
		List<SpaceActionRelation> newRel = Lists.newArrayList();
		// frame
		String frameId = req.getResourceId();
		upperIds.add(frameId);
		// target
		List<SpaceActionFrameLevel> targetLevels = req.getChildren();
		if (null != targetLevels) {
			for (SpaceActionFrameLevel targetLevel : targetLevels) {
				String targetId = targetLevel.getResourceId();
				upperIds.add(targetId);
				newRel.add(this.createRel(frameId, targetId));
				// method
				List<SpaceActionFrameLevel> methodLevels = targetLevel.getChildren();
				if (null == methodLevels) {
					continue;
				}
				for (SpaceActionFrameLevel methodLevel : methodLevels) {
					String methodId = methodLevel.getResourceId();
					upperIds.add(methodId);
					newRel.add(this.createRel(targetId, methodId));
					// action
					List<SpaceActionFrameLevel> actionLevels = methodLevel.getChildren();
					if (null == actionLevels) {
						continue;
					}
					for (SpaceActionFrameLevel actionLevel : actionLevels) {
						String actionId = actionLevel.getResourceId();
						newRel.add(this.createRel(methodId, actionId));
					}
				}
			}
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				relationService.deleteByUpperIds(upperIds);
				relationService.saveBatch(newRel);
			}
		});

	}

	private SpaceActionRelation createRel(String upperId, String lowerId) {
		SpaceActionRelation rel = new SpaceActionRelation();
		rel.setUpperId(upperId);
		rel.setLowerId(lowerId);
		return rel;
	}
}