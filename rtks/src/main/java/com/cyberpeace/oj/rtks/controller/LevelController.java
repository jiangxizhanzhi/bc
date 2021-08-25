package com.cyberpeace.oj.rtks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.frame.LevelAddInput;
import com.cyberpeace.oj.rtks.controller.frame.LevelInput;
import com.cyberpeace.oj.rtks.controller.frame.LevelModifyInput;
import com.cyberpeace.oj.rtks.controller.frame.LevelOutput;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-框架层级管理")
@RestController
@RequestMapping("/rtks/frame/level")
public class LevelController extends BaseController<Level, LevelInput, LevelAddInput, LevelModifyInput, LevelOutput, LevelOutput, LevelService> {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	@Override
	LevelService getService() {
		return levelService;
	}

	@Override
	Level getEntityFromAddInput(LevelAddInput levelAddInput) {
		return getLevelFromInput(levelAddInput, null);
	}

	@Override
	Level getEntityFromModifyInput(LevelModifyInput levelModifyInput, Level originEntity) {
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
		roleService.ensureLevelNotChildRole(parentId);
		// 确保parentId对应的框架/层级存在
		Level parentLevel = levelService.getLevelOrFrame(parentId);
		// 迭代获取当前层级所属框架
		int maxLoop = 10;
		level.setFrame(levelService.getFrame(parentLevel, maxLoop - 1).getResourceId());

		return level;
	}

	@Override
	LevelOutput getOutputFromEntity(Level level) {
		LevelOutput output = new LevelOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());
		output.setParent(level.getParent());

		return output;
	}

	@Override
	LevelOutput getListOutputFromEntity(Level entity) {
		// 层级的列表查询不被允许
		return null;
	}

	@ApiOperation("忽略本接口")
	@GetMapping("/list")
	@Override
	public BaseRsp<ListRsp<LevelOutput>> list(ListReq<Level> listReq) {
		// 层级的列表查询不被允许
		return null;
	}

}
