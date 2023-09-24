package com.cyberpeace.cr.singletrain.service.bo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("训练历史")
public class TrainHistoryBo {
    @ApiModelProperty("训练房间id")
    private String roomId;

    @ApiModelProperty("训练时间")
    private Date trainTime;

    @ApiModelProperty("训练分值")
    private Double score;

    @ApiModelProperty("训练时间倒序索引")
    private Integer trainTimes;
}
