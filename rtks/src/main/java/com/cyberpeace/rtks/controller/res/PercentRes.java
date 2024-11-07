package com.cyberpeace.rtks.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 百分之结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("进度数据")
public class PercentRes implements Serializable {

    /**
     * x 个名称
     */
    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

    /**
     * y 百分比
     */
    @ApiModelProperty("y轴，数值")
    private List<Integer> yPercents;

}
