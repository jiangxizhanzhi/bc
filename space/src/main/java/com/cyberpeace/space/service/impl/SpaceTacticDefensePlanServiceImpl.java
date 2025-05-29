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

/**
 * 空间战法防御计划Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceTacticDefensePlanService
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **代码概述** 
 * 该代码是一个服务类 `SpaceTacticDefensePlanServiceImpl`，实现了 `SpaceTacticDefensePlanService` 接口。它主要负责处理与“空间战法防御计划”相关的业务逻辑，包括获取框架层级、更新框架层级等操作。 
 * --- 
 * #### 2. **关键功能** 
 * - **获取框架层级 (`getFrameLevels` 方法)** 
 * - 根据传入的 `SpaceTacticDefensePlan` 对象，解析其防御计划 (`plan`) 中的防御 ID 列表。 
 * - 调用 `defenseService` 查询对应的防御对象列表，并按类别分组。 
 * - 构建一个树形结构的框架层级 (`SpaceFrameLevel`)，包含类别及其下属的防御对象。 
 * - **更新框架层级 (`renewFrame` 方法)** 
 * - 根据传入的请求体 (`DefensePlanLevelUpdateReq`)，获取需要更新的防御对象。 
 * - 检查当前框架层级是否合法，并根据新的类别顺序插入或更新防御对象。 
 * - 返回更新后的框架层级。 
 * --- 
 * #### 3. **依赖的服务** 
 * - `SpaceTacticDefenseService`：用于查询防御对象、类别列表等数据。 
 * - `JsonUtil` 和 `StreamApiUtils`：提供 JSON 解析和流式操作工具。 
 * --- 
 * #### 4. **技术亮点** 
 * - **树形结构构建** 
 * 使用递归或嵌套的方式构建树形结构，便于展示复杂的层级关系。 
 * - **流式操作** 
 * 使用 `StreamApiUtils` 提供的工具方法（如 `groupBy` 和 `toMap`），简化了数据处理逻辑。 
 * - **异常处理** 
 * 尽管代码中没有显式的异常捕获，但通过检查返回值（如 `defenseList.isEmpty()`）来避免潜在问题。 
 * --- 
 * #### 5. **潜在改进点** 
 * - **注释优化** 
 * 部分注释较为简略，可以进一步细化以帮助理解复杂逻辑。 
 * - **异常处理** 
 * 当前代码未对异常进行捕获，建议在关键步骤中加入异常处理逻辑。 
 * - **性能优化** 
 * 如果数据量较大，`listByIds` 和 `listCategory` 的多次调用可能影响性能，可考虑缓存机制。 
 * --- 
 * #### 6. **总结** 
 * 该代码逻辑清晰，功能完整，适用于处理复杂的树形层级结构。通过合理的工具类使用和模块化设计，代码具有较高的可读性和扩展性。 
**/

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
	 * @throws
	 * @see SpaceTacticDefensePlan
	 * @see SpaceFrameLevel
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

	/**
	 * 更新框架
	 *
	 * @param req 请求体
	 * @return 动作框架层级
	 * @throws
	 * @see DefensePlanLevelUpdateReq
	 * @see SpaceFrameLevel
	 */
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

