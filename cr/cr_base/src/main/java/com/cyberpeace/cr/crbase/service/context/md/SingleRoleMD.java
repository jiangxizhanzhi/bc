package com.cyberpeace.cr.crbase.service.context.md;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.cs.service.context.RoleBo;
import com.cyberpeace.cs.service.context.paraser.TaskParser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SingleRoleMD implements Serializable {

	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;

}
