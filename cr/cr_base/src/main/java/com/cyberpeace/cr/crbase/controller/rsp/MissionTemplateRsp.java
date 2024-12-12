package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 任务模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplateRsp {
    /**
     * 资源id
     */
    @ApiModelProperty("任务id")
    private String resourceId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 得分
     */
    @ApiModelProperty(value = "分值")
    private Integer score;

    /**
     * 技能点
     */
    @ApiModelProperty(value = "技能点")
    private List<Integer> skillPoints;

    /**
     * 答案
     */
    @ApiModelProperty(value = "标准答案")
    private String answer;

    /**
     * 选择
     */
    @ApiModelProperty(value = "选择题选项，非选择题为\"null\"")
    private String option;

    /**
     * 类型
     */
    @ApiModelProperty(value = "任务类型（单选、填空等）")
    private Integer type;

    /**
     * 类别
     */
    @ApiModelProperty(value = "任务类别（resource_id）")
    private String category;

    /**
     * 写入向上
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

}
