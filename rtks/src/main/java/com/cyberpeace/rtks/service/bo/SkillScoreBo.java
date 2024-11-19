package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 技能得分Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("技能得分")
public class SkillScoreBo implements Serializable {

	/**
	 * 技能编号
	 *
	 * @see String
	 */
	@ApiModelProperty("技能编号")
    private String skillId;

	/**
	 * 技能内容
	 *
	 * @see String
	 */
	@ApiModelProperty("技能内容")
    private String skillContent;

	/**
	 * 技能得分
	 *
	 * @see String
	 */
	@ApiModelProperty("技能得分")
    private String score;

	/**
	 * 权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("权重")
    private Integer weight;

}
