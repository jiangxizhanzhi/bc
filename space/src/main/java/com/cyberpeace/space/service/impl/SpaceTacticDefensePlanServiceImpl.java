
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceTacticDefense;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefensePlanMapper;
import com.cyberpeace.space.service.SpaceTacticDefensePlanService;
import com.cyberpeace.space.service.SpaceTacticDefenseService;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;
import com.cyberpeace.space.service.context.tactic.TacticDefenseCategoryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpaceTacticDefensePlanServiceImpl extends ServiceImpl<SpaceTacticDefensePlanMapper, SpaceTacticDefensePlan> implements SpaceTacticDefensePlanService {

	/**
	 * 防御Service
	 */
	@Autowired
	private SpaceTacticDefenseService defenseService;

	/**
	 * 获取框架水平
	 *
	 * @param plan 计划
	 * @return {@code SpaceFrameLevel }
	 */
	@Override
	public SpaceFrameLevel getFrameLevels(SpaceTacticDefensePlan plan) {
		// shape level
		SpaceFrameLevel level = new SpaceFrameLevel(plan.getResourceId(), plan.getName());
		// ids
		List<String> defenseIds = JsonUtil.toJsonObjectArray(plan.getPlan(), String.class);
		// get defense list
		List<SpaceTacticDefense> defenseList = defenseService.listByIds(defenseIds);
		if (defenseList.isEmpty()) {
			return level;
		}
		Map<String, List<SpaceTacticDefense>> defenseMap = StreamApiUtils.groupBy(defenseList,
				SpaceTacticDefense::getCategoryId);
		// get categories in order
		List<TacticDefenseCategoryBo> categoryBos = defenseService.listCategory();

		// get related category
		for (TacticDefenseCategoryBo categoryBo : categoryBos) {
			if (defenseMap.containsKey(categoryBo.getResourceId())) {
				SpaceFrameLevel categoryLevel = new SpaceFrameLevel(categoryBo.getResourceId(),
						categoryBo.getName());
				Map<String, SpaceTacticDefense> map = StreamApiUtils.toMap(
						defenseMap.get(categoryBo.getResourceId()),
						SpaceTacticDefense::getResourceId, d -> d);
				for (String defenseId : defenseIds) {
					if (map.containsKey(defenseId)) {
						categoryLevel.getChildren()
								.add(new SpaceFrameLevel(defenseId, map.get(defenseId).getName()));
					}
				}
				level.getChildren().add(categoryLevel);
			}
		}
		return level;
	}

	@Override
	public SpaceFrameLevel renewFrame(DefensePlanLevelUpdateReq req) {
		// get defense
		SpaceTacticDefense defense = defenseService.getById(req.getResourceId());
		// get cate in order
		List<TacticDefenseCategoryBo> categoryBoList = defenseService.listCategory();
		SpaceFrameLevel level = req.getLevel();
		// validate
		level.checkLevel(true);
		// get new cate order
		List<String> categoryIds = StreamApiUtils.map(categoryBoList,
				TacticDefenseCategoryBo::getResourceId);
		int index = categoryIds.indexOf(defense.getCategoryId());
		int curIndex = 0;
		boolean added = false;
		for (int i = 0; i < level.getChildren().size(); i++) {
			SpaceFrameLevel categoryLevel = level.getChildren().get(i);
			if (categoryLevel.getResourceId().equals(defense.getCategoryId())) {
				categoryLevel.getChildren()
						.add(new SpaceFrameLevel(defense.getResourceId(), defense.getName()));
				added = true;
			} else {
				// find position to insert new category
				curIndex = categoryIds.indexOf(categoryLevel.getResourceId());
				if (curIndex > index) {
					SpaceFrameLevel newCategoryLevel = new SpaceFrameLevel(
							categoryBoList.get(index).getResourceId(),
							categoryBoList.get(index).getName());
					newCategoryLevel.getChildren()
							.add(new SpaceFrameLevel(defense.getResourceId(), defense.getName()));
					level.getChildren().add(i, newCategoryLevel);
					added = true;
				}
			}
			if (added) {
				break;
			}
		}
		// if last category
		if (!added) {
			SpaceFrameLevel newCategoryLevel = new SpaceFrameLevel(
					categoryBoList.get(index).getResourceId(), categoryBoList.get(index).getName());
			newCategoryLevel.getChildren()
					.add(new SpaceFrameLevel(defense.getResourceId(), defense.getName()));
			level.getChildren().add(newCategoryLevel);
		}
		return level;
	}
}
