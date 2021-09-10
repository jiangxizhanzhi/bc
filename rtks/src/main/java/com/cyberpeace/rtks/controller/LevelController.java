package com.cyberpeace.rtks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.suit.BaseController;
import com.cyberpeace.rtks.controller.frame.LevelAddInput;
import com.cyberpeace.rtks.controller.frame.LevelInput;
import com.cyberpeace.rtks.controller.frame.LevelModifyInput;
import com.cyberpeace.rtks.controller.frame.LevelOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RoleService;

import io.swagger.annotations.Api;

@Api(tags = "TRKS-框架层级管理")
@RestController
@RequestMapping("/rtks/frame/level")
public class LevelController extends BaseController<Level, LevelAddInput, LevelModifyInput, LevelOutput> {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Override
	protected LevelService getService() {
		return levelService;
	}

	@Override
	protected Level getOr404(String resourceId) {
		return levelService.getOr404(resourceId);
	}

	@Override
	protected Level getEntityFromAddInput(LevelAddInput levelAddInput) {
		return getLevelFromInput(levelAddInput, null);
	}

	@Override
	protected Level getEntityFromModifyInput(LevelModifyInput levelModifyInput,
			Level originEntity) {
		Level level = getLevelFromInput(levelModifyInput, originEntity);
		level.setResourceId(levelModifyInput.getResourceId());
		return level;
	}

	private Level getLevelFromInput(LevelInput input, Level originEntity) {
		Level level = new Level();
		level.setName(input.getName());
		level.setDescription(input.getDescription());

		String parentId = input.getParent();
		level.setParent(parentId);

		// 修改时parentId参数未传，无改动
		if (parentId == null) {
			return level;
		}

		// 修改时parentId参数无改动
		if (originEntity != null && originEntity.getParent().equals(parentId)) {
			return level;
		}

		// 已经绑定了岗位的层级不允许新增子层级
		List<Role> roles = roleService.getRolesByLevelId(parentId);
		if (!CollectionUtils.isEmpty(roles)) {
			throw new BusinessException(RtksBusinessErrorCode.UNABLE_ADD_FRAME);
		}

		// 确保parentId对应的框架/层级存在
		Level parentLevel = levelService.getLevelOrFrameOr404(parentId);
		// 迭代获取当前层级所属框架
		level.setFrame(levelService.getFrame(parentLevel).getResourceId());

		return level;
	}

	@Override
	protected LevelOutput getOutputFromEntity(Level level) {
		LevelOutput output = new LevelOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());
		output.setParent(level.getParent());

		return output;
	}

	@Override
	protected void performBatchDelete(List<String> ids) {
		// 验证输入层级id合法
		List<Level> levels = levelService.getLevelsByIds(ids);
		List<String> levelIds = new ArrayList<>();
		for (Level level : levels) {
			levelIds.add(level.getResourceId());
		}

		// 如果层级下有岗位绑定，不允许删除
		List<String> roleIds = roleService.getRoleIdsByLevelIds(ids);
		if (!CollectionUtils.isEmpty(roleIds)) {
			throw new BusinessException(RtksBusinessErrorCode.UNABLE_DELETE_FRAME);
		}

		// 把待删除层级下的层级绑定到其父层级/框架上
		// 待删除的层级-转移到的层级映射表
		Map<String, String> changeMap = new HashMap<>();
		for (Level level : levels) {
			String levelId = level.getResourceId();
			String target = getAncestorExclude(level, levelIds);
			changeMap.put(levelId, target);
		}

		// 更改子层级的parent属性
		for (String levelId : changeMap.keySet()) {
			String target = changeMap.get(levelId);
			levelService.changeLevelId(levelId, target);
		}

		levelService.removeByIds(levelIds);
	}

	/**
	 * 给定层级(非框架) 获取其父层级/框架id 如果该id在给定levelIds数组中，则再迭代查找
	 * */
	private String getAncestorExclude(@NotNull Level level, @NotNull List<String> levelIds) {
		String parentId = level.getParent();
		if (!levelIds.contains(parentId)) {
			return parentId;
		}

		levelIds.remove(parentId);

		Level parent = levelService.getById(parentId);
		return getAncestorExclude(parent, levelIds);
	}

}
