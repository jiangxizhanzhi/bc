package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 任务模板响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class MissionTemplateRsp {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("任务id")
    private String resourceId;

    /**
     * 标题
     *
     * @see String
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty(value = "分值")
    private Integer score;

    /**
     * 技能点
     *
     * @see List<Integer>
     */
    @ApiModelProperty(value = "技能点")
    private List<Integer> skillPoints;

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty(value = "标准答案")
    private String answer;

    /**
     * 选择
     *
     * @see String
     */
    @ApiModelProperty(value = "选择题选项，非选择题为\"null\"")
    private String option;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty(value = "任务类型（单选、填空等）")
    private Integer type;

    /**
     * 类别
     *
     * @see String
     */
    @ApiModelProperty(value = "任务类别（resource_id）")
    private String category;

    /**
     * 写入向上
     *
     * @see String
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

}
