package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.cr.crbase.service.context.paraser.TaskParser;
import com.cyberpeace.cs.service.context.RoleBo;

import lombok.Data;

/**
 * 团队、红蓝角色Markdown
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TeamAndRBMD implements Serializable {

	/**
	 * 节点集合
	 */
	@MdHeadLine(level = 1, name = "节点集合")
	private String node;

	/**
	 * 战争迷雾
	 */
	@MdHeadLine(level = 2, name = "战争迷雾", parser = ListParser.class)
	private List<String>  seeNode;

	/**
	 * 用户资产
	 */
	@MdHeadLine(level = 2, name = "用户资产", parser = ListParser.class)
	private List<String>  useNode;

	/**
	 * 任务集合
	 */
	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;
}
