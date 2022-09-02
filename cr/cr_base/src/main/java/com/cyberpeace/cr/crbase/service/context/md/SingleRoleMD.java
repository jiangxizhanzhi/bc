package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.cr.crbase.service.context.paraser.TaskParser;
import com.cyberpeace.cs.service.context.RoleBo;

import lombok.Data;

/**
 * 单兵训练角色MarkDown
 */
@Data
public class SingleRoleMD implements Serializable {

	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;

}
