package com.cyberpeace.rtks.controller;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.suit.ListController;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.rtks.controller.frame.FrameAddInput;
import com.cyberpeace.rtks.controller.frame.FrameModifyInput;
import com.cyberpeace.rtks.controller.frame.FrameOutput;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.service.FrameService;
import com.cyberpeace.rtks.service.LevelService;
import com.cyberpeace.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "TRKS-框架管理")
@RestController
@RequestMapping("/rtks/frame")
public class FrameController extends ListController<Level, FrameAddInput, FrameModifyInput, FrameOutput, FrameOutput> {

	@Autowired
	private FrameService frameService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private RoleService roleService;

	private Boolean singleFrame;

	private void multiFrameOnly() {
		if (singleFrame) {
			throw new InvalidParamException("单框架模式下禁用");
		}
	}

	private void singleFrameOnly() {
		if (!singleFrame) {
			throw new InvalidParamException("多框架模式下禁用");
		}
	}

	/**
	 * 单框架模式下 默认的框架id
	 */
	private final String DEFAULT_FRAME_ID = "default";

	@PostConstruct
	public void init() {
		String singleFrameConfig = ConfigUtil.getContextProperty("singleFrame");
		singleFrame = Objects.equals(singleFrameConfig, "true");
	}

	@Override
	protected FrameService getService() {
		return frameService;
	}

	@Override
	protected Level getOr404(String resourceId) {
		return frameService.getOr404(resourceId);
	}

	@Override
	protected Level getEntityFromAddInput(FrameAddInput frameAddInput) {
		Level frame = new Level();
		frame.setName(frameAddInput.getName());
		frame.setDescription(frameAddInput.getDescription());
		return frame;
	}

	@Override
	protected Level getEntityFromModifyInput(FrameModifyInput frameModifyInput, Level originEntity) {
		Level frame = new Level();
		if (!singleFrame) {
			// 单框架模式下框架名称不可变
			frame.setName(frameModifyInput.getName());
		}
		frame.setDescription(frameModifyInput.getDescription());
		frame.setResourceId(frameModifyInput.getResourceId());
		return frame;
	}

	@Override
	protected FrameOutput getOutputFromEntity(Level frame) {
		FrameOutput output = new FrameOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());

		return output;
	}

	@Override
	protected IPage<Level> listEntity(Page<Level> page, String keyword) {
		multiFrameOnly();
		return frameService.listEntity(page, keyword);
	}

	@Override
	protected FrameOutput getListOutputFromEntity(Level frame) {
		return getOutputFromEntity(frame);
	}

	@Override
	protected void performBatchDelete(List<String> frameIds) {
		multiFrameOnly();
		// 检查输入
		frameIds = frameService.checkFrameIds(frameIds);

		// 获取框架对应层级
		List<String> levelIds = levelService.getLevelIdsByFrameIds(frameIds);

		// 获取层级对应岗位
		List<String> roleIds = roleService.getRoleIdsByLevelIds(levelIds);

		// 删除岗位
		roleService.removeByIds(roleIds);

		// 删除层级
		levelService.removeByIds(levelIds);

		// 删除框架
		frameService.removeByIds(frameIds);
	}

	@ApiOperation("指定框架层级数据的全量树形展示")
	@GetMapping("/tree")
	public BaseRsp<LevelTreeOutput> getTree(String resource_id) {
		return new BaseRsp<>(frameService.getTree(resource_id));
	}

	@ApiOperation("清空默认框架，仅单框架模式下有效")
	@PostMapping("/clear")
	public BaseRsp<FrameOutput> clearFrame() {
		singleFrameOnly();
		// 除根节点外删除所有框架、层级
		levelService.removeAllLevels();
		roleService.removeAll();
		return retrieve(DEFAULT_FRAME_ID);
	}

	@ApiOperation("重置默认框架，仅单框架模式下有效")
	@PostMapping("/reset")
	public BaseRsp<FrameOutput> resetFrame() {
		singleFrameOnly();
		// 重置所有框架、层级
		levelService.initDefaultFromPreset();
		roleService.initDefaultFromPreset();
		return retrieve(DEFAULT_FRAME_ID);
	}
}
