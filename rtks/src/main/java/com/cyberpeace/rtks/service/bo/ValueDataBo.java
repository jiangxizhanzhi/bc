package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 值数据博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("计算中间存储")
public class ValueDataBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("资源编号")
    private String resourceId;

    /**
     * 内容
     */
    @ApiModelProperty("技能或者知识点或者课程名称或者代号")
    private String content;

    /**
     * 过程
     */
    @ApiModelProperty("进度或者得分")
    private Double process = new Double(0);

    /**
     * 重量
     */
    @ApiModelProperty("权重")
    private Double weight = new Double(1);

    /**
     * 课计数
     */
    @JsonIgnore
    private Double lessonCount = new Double(0);

    /**
     * 完成计数
     */
    @JsonIgnore
    private Double completeCount = new Double(0);

}
