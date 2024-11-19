package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 值数据Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("计算中间存储")
public class ValueDataBo implements Serializable {

    /**
     * 资源编号
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
    private String resourceId;

    /**
     * 技能或者知识点或者课程名称或者代号
     *
     * @see String
     */
    @ApiModelProperty("技能或者知识点或者课程名称或者代号")
    private String content;

    /**
     * 进度或者得分
     *
     * @see Double
     */
    @ApiModelProperty("进度或者得分")
    private Double process = (double) 0;

    /**
     * 权重
     *
     * @see Double
     */
    @ApiModelProperty("权重")
    private Double weight = 1.0;

    /**
     * 课时计数
     *
     * @see Double
     */
    @JsonIgnore
    private Double lessonCount = (double) 0;

    /**
     * 完成计数
     *
     * @see Double
     */
    @JsonIgnore
    private Double completeCount = (double) 0;

}
