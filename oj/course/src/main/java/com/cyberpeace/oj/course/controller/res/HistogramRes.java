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
 * @date 2024/11/08
 */
@Data
@ApiModel("进度数据")
public class HistogramRes implements Serializable {

    /**
     * x 个名称
     */
    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

    /**
     * y 值
     */
    @ApiModelProperty("y轴，柱形值")
    private List<Integer> yValues;

}
