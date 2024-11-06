package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.*;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		SpaceActionFrame frame = this.getOrException(resourceId);
		SpaceActionFrameLevel level = JsonUtil.toJsonObject(frame.getFrame(),
				SpaceActionFrameLevel.class);
		if (null == level) {
			return new SpaceActionFrameLevel(resourceId, frame.getName());
		}

		// gather ids
		List<String> targetIds = Lists.newArrayList();
		List<SpaceActionTarget> targetList = Lists.newArrayList();
		List<String> methodIds = Lists.newArrayList();
		List<SpaceActionMethod> methodList = Lists.newArrayList();
		List<String> actionIds = Lists.newArrayList();
		List<SpaceAction> actionList = Lists.newArrayList();
		for (SpaceActionFrameLevel target : level.getChildren()) {
			targetIds.add(target.getResourceId());
			for (SpaceActionFrameLevel method : target.getChildren()) {
				methodIds.add(method.getResourceId());
				for (SpaceActionFrameLevel action : method.getChildren()) {
					actionIds.add(action.getResourceId());
				}
			}
		}
		// get entities
		if (!targetIds.isEmpty()) {
			targetList = targetService.listByIds(targetIds);
		}
		if (!methodIds.isEmpty()) {
			methodList = methodService.listByIds(methodIds);
		}
		if (!actionIds.isEmpty()) {
			actionList = actionService.listByIds(actionIds);
		}
		// mapping
		Map<String, SpaceActionTarget> targetMap = StreamApiUtils.toMap(targetList,
				SpaceActionTarget::getResourceId, t -> t);
		Map<String, SpaceActionMethod> methodMap = StreamApiUtils.toMap(methodList,
				SpaceActionMethod::getResourceId, t -> t);
		Map<String, SpaceAction> actionMap = StreamApiUtils.toMap(actionList,
				SpaceAction::getResourceId, t -> t);
		// set name
		int i = 0;
		while (i < level.getChildren().size()) {
			SpaceActionFrameLevel target = level.getChildren().get(i);
			String targetId = target.getResourceId();
			if (targetMap.containsKey(targetId)) {
				target.setName(targetMap.get(targetId).getName());
				int j = 0;
				while (j < target.getChildren().size()) {
					SpaceActionFrameLevel method = target.getChildren().get(j);
					String methodId = method.getResourceId();
					if (methodMap.containsKey(methodId)) {
						method.setName(methodMap.get(methodId).getName());
						int k = 0;
						while (k < method.getChildren().size()) {
							SpaceActionFrameLevel action = method.getChildren().get(k);
							String actionId = action.getResourceId();
							if (actionMap.containsKey(actionId)) {
								action.setName(actionMap.get(actionId).getName());
								k += 1;
							} else {
								method.getChildren().remove(k);
							}
						}
						j += 1;
					} else {
						target.getChildren().remove(j);
					}
				}
				i += 1;
			} else {
				level.getChildren().remove(i);
			}
		}
		return level;
	}

	/**
	 * 保存框架
	 *
	 * @param req 请求体
	 */
	@Override
	public void saveFrame(SpaceActionFrameLevel req) {
		this.checkFrame(req);
		SpaceActionFrame frame = this.getOrException(req.getResourceId());
		frame.setFrame(JsonUtil.toJsonString(req));
		this.updateById(frame);
	}

	private SpaceActionRelation createRel(String upperId, String lowerId) {
		SpaceActionRelation rel = new SpaceActionRelation();
		rel.setUpperId(upperId);
		rel.setLowerId(lowerId);
		return rel;
	}

	private SpaceActionFrame getOrException(String resourceId) {
		SpaceActionFrame frame = this.getById(resourceId);
		if (null == frame) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return frame;
	}

	/**
	 * 检查框架
	 *
	 * @param frame 框架
	 */
	private void checkFrame(SpaceActionFrameLevel frame) {
		if (null == frame.getChildren()) {
			frame.setChildren(Lists.newArrayList());
		}
		if (StringUtils.isBlank(frame.getResourceId())) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		List<SpaceActionFrameLevel> childSet = StreamApiUtils.distinctByColumn(frame.getChildren(),
				SpaceActionFrameLevel::getResourceId);
		if (childSet.size() != frame.getChildren().size()) {
			throw new BusinessException(ErrorCode.DUPLICATE_ID);
		}
		for (SpaceActionFrameLevel child : frame.getChildren()) {
			checkFrame(child);
		}
	}
}