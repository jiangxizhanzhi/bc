package com.cyberpeace.oj.rtks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.rtks.controller.frame.*;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.service.FrameService;
import com.cyberpeace.oj.rtks.service.LevelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class FrameController extends BaseController<Level, FrameInput, FrameAddInput, FrameModifyInput, FrameOutput, FrameOutput, FrameService> {

	@Autowired
	private FrameService frameService;

	@Autowired
	private LevelService levelService;

	@Override
	FrameService getService() {
		return frameService;
	}

	@Override
	Level getEntityFromAddInput(FrameAddInput frameAddInput) {
		Level frame = new Level();
		frame.setName(frameAddInput.getName());
		frame.setDescription(frameAddInput.getDescription());
		return frame;
	}

	@Override
	Level getEntityFromModifyInput(FrameModifyInput frameModifyInput, Level originEntity) {
		Level frame = new Level();
		frame.setName(frameModifyInput.getName());
		frame.setDescription(frameModifyInput.getDescription());
		frame.setResourceId(frameModifyInput.getResourceId());
		return frame;
	}

	@Override
	FrameOutput getOutputFromEntity(Level frame) {
		FrameOutput output = new FrameOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());;

		return output;
	}

	@Override
	FrameOutput getListOutputFromEntity(Level frame) {
		return getOutputFromEntity(frame);
	}

	@ApiOperation("指定框架层级数据的全量树形展示")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> getTree(String resource_id) {
		// {父级层级id:[子层级对象]}
		Map<String, List<LevelTreeOutput>> levelTreeOutputMap = new HashMap<>();
		// 所有层级输出对象
		List<LevelTreeOutput> levelTreeOutputs = new ArrayList<>();

		// 当前框架下所有的层级(不包括框架本身)
		List<Level> levels = levelService.getLevelsByFrameId(resource_id);
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
		Level frame = frameService.getOr404(resource_id);
		LevelTreeOutput frameTree = getLevelTreeOutput(frame);
		levelTreeOutputs.add(frameTree);

		// 遍历框架+层级，赋予children值
		for (LevelTreeOutput levelTreeOutput : levelTreeOutputs) {
			List<LevelTreeOutput> childLevels = levelTreeOutputMap
					.getOrDefault(levelTreeOutput.getResourceId(), null);
			levelTreeOutput.setChildren(childLevels);
		}

		return new BaseRsp<>(frameTree);
	}

	private LevelTreeOutput getLevelTreeOutput(Level level) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(level.getResourceId());
		output.setName(level.getName());
		output.setDescription(level.getDescription());

		return output;
	}

}
