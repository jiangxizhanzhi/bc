package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("进度数据")
public class HistogramRes implements Serializable {

    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

    @ApiModelProperty("y轴，柱形值")
    private List<Integer> yValues;

}
