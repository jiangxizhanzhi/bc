package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("分类技能得分")
public class CategorySkillBo implements Serializable {

	/**
	 * 分类编号
	 */
    @ApiModelProperty("分类编号")
    private String resourceId;

	/**
	 * 分类名称
	 */
    @ApiModelProperty("分类名称")
    private String content;

	/**
	 * 技能得分列表
	 */
    @ApiModelProperty("技能得分列表")
    private List<SkillScoreBo> skills;
}
