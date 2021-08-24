package com.cyberpeace.oj.rtks.controller.frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.RoleService;

@Service
public class FramePorter {

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	public Level getFrameFromInput(FrameInput input) {
		Level frame = new Level();
		frame.setName(input.getName());
		frame.setDescription(input.getDescription());
		if (input instanceof FrameModifyInput) {
			frame.setResourceId(((FrameModifyInput) input).getResourceId());
		}
		return frame;
	}

	public Level getLevelFromInput(LevelInput input) {
		Level level = new Level();
		level.setName(input.getName());
		level.setDescription(input.getDescription());

		String parentId = input.getParent();
		level.setParent(parentId);
		if (parentId != null) {
			// 已经绑定了岗位的层级不允许新增子层级
			roleService.ensureLevelNotChildRole(level.getParent());
		}

		if (input instanceof LevelModifyInput) {
			level.setResourceId(((LevelModifyInput) input).getResourceId());
		}
		return level;
	}

	public FrameOutput getFrameOutput(Level frame) {
		FrameOutput output = new FrameOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());;

		return output;
	}

	public LevelOutput getLevelOutput(Level level) {
		LevelOutput output = new LevelOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());
		output.setParent(level.getParent());

		return output;
	}

	public LevelTreeOutput getLevelTree(String frameId) {
		// {父级层级id:[子层级对象]}
		Map<String, List<LevelTreeOutput>> levelTreeOutputMap = new HashMap<>();
		// 所有层级输出对象
		List<LevelTreeOutput> levelTreeOutputs = new ArrayList<>();

		// 当前框架下所有的层级(不包括框架本身)
		List<Level> levels = levelService.getLevelsByFrameId(frameId);
		for (Level level : levels) {
			String parentId = level.getParent();
			levelService.ensureNotFrame(level);

			if (!levelTreeOutputMap.containsKey(parentId)) {
				levelTreeOutputMap.put(parentId, new ArrayList<>());
			}

			LevelTreeOutput levelTreeOutput = getLevelTreeOutput(level);
			levelTreeOutputMap.get(parentId).add(levelTreeOutput);
			levelTreeOutputs.add(levelTreeOutput);
		}

		// 加入框架本身
		Level frame = levelService.getFrameById(frameId);
		LevelTreeOutput frameTree = getLevelTreeOutput(frame);
		levelTreeOutputs.add(frameTree);

		// 遍历框架+层级，赋予children值
		for (LevelTreeOutput levelTreeOutput : levelTreeOutputs) {
			List<LevelTreeOutput> childLevels = levelTreeOutputMap
					.getOrDefault(levelTreeOutput.getResourceId(), null);
			levelTreeOutput.setChildren(childLevels);
		}

		return frameTree;
	}

	private LevelTreeOutput getLevelTreeOutput(Level level) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());

		return output;
	}
}
