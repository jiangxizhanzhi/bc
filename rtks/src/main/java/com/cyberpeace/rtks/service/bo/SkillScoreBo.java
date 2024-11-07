package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 技能得分 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("技能得分")
public class SkillScoreBo implements Serializable {

    /**
     * 技能id
     */
    @ApiModelProperty("技能编号")
    private String skillId;

    /**
     * 技能内容
     */
    @ApiModelProperty("技能内容")
    private String skillContent;

    /**
     * 得分
     */
    @ApiModelProperty("技能得分")
    private String score;

    /**
     * 重量
     */
    @ApiModelProperty("权重")
    private Integer weight;

}
