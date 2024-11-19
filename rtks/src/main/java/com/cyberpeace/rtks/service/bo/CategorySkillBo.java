package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类别技能Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("分类技能得分")
public class CategorySkillBo implements Serializable {

	/**
	 * 分类编号
	 *
	 * @see String
	 */
	@ApiModelProperty("分类编号")
    private String resourceId;

	/**
	 * 分类名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类名称")
    private String content;

	/**
	 * 技能得分列表
	 *
	 * @see List<SkillScoreBo>
	 */
	@ApiModelProperty("技能得分列表")
    private List<SkillScoreBo> skills;
}
