package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 类别 技能 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("分类技能得分")
public class CategorySkillBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("分类编号")
    private String resourceId;

    /**
     * 内容
     */
    @ApiModelProperty("分类名称")
    private String content;

    /**
     * 技能
     */
    @ApiModelProperty("技能得分列表")
    private List<SkillScoreBo> skills;
}
