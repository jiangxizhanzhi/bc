package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能过程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillResBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillProcessRes extends SkillResBase implements Serializable {

	/**
	 * 完成度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成度")
	private Integer process = 0;

	/**
	 * 任务完成度
	 *
	 * @see List<MissionProcessBo>
	 */
	@ApiModelProperty("任务完成度")
	private List<MissionProcessBo> missions = Lists.newArrayList();
}
