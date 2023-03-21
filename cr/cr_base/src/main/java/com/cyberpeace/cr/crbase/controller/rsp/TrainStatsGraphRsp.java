package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainStatsGraphRsp implements Serializable {
    @ApiModelProperty("训练类型")
    private List<String> label;

    @ApiModelProperty("训练数量")
    private List<Integer> value;

    @ApiModelProperty("总数")
    private Integer count;
}
