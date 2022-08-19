package com.cyberpeace.cr.crbase.service.context.md;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.cs.service.context.RoleBo;
import com.cyberpeace.cs.service.context.paraser.TaskParser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeamAndRBMD implements Serializable {

	@MdHeadLine(level = 1, name = "节点集合")
	private String node;

	@MdHeadLine(level = 2, name = "战争迷雾", parser = ListParser.class)
	private List<String>  seeNode;

	@MdHeadLine(level = 2, name = "用户资产", parser = ListParser.class)
	private List<String>  useNode;

	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;
}
