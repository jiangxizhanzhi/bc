package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 直方图结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("进度数据")
public class HistogramRes implements Serializable {

    /**
     * x 个名称
     *
     * @see List<String>
     */
    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

    /**
     * y 值
     *
     * @see List<Integer>
     */
    @ApiModelProperty("y轴，柱形值")
    private List<Integer> yValues;

}
