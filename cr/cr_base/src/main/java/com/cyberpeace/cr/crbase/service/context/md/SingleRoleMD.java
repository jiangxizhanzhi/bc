package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.cr.crbase.service.context.paraser.TaskParser;
import com.cyberpeace.cs.service.context.RoleBo;

import lombok.Data;

/**
 * 单兵训练角色MarkDown
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SingleRoleMD implements Serializable {

	/**
	 * 任务
	 *
	 * @see List<RoleBo>
	 */
	@MdHeadLine(level = 1, name = "任务集合", parser = TaskParser.class)
	private List<RoleBo> task;

}
