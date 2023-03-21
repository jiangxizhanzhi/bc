package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainStatsAccGraphRsp implements Serializable {
    @ApiModelProperty("训练类型")
    private List<String> label;

    @ApiModelProperty("已开始统计数量")
    private List<Integer> startValue;

    @ApiModelProperty("未开始统计数量")
    private List<Integer> notStartValue;

    @ApiModelProperty("总数")
    private Integer count;
}
