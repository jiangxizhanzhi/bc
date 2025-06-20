package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.cr.crbase.service.context.paraser.TaskParser;
import com.cyberpeace.cs.service.context.RoleBo;

import lombok.Getter;
import lombok.Setter;

/**
 * 团队、红蓝角色Markdown
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TeamAndRBMD implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 节点
	 */
	@MdHeadLine(level = 1, name = "节点集合")
	private String node;

	/**
	 * 请参阅 Node
	 */
	@MdHeadLine(level = 2, name = "战争迷雾", parser = ListParser.class)
	private List<String>  seeNode;

	/**
	 * 使用节点
	 */
	@MdHeadLine(level = 2, name = "用户资产", parser = ListParser.class)
	private List<String>  useNode;

	/**
	 * 任务
	 */
	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;
}
