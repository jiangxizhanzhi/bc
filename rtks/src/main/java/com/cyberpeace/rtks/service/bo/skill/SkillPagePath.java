package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能分页路径
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillResBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillPagePath extends SkillResBase implements Serializable {

	/**
	 * 任务列表
	 *
	 * @see List<MissionPathPageBo>
	 */
	@ApiModelProperty("任务列表")
	private List<MissionPathPageBo> missions = Lists.newArrayList();
}
