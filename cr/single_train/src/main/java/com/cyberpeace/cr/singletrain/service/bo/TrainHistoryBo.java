package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("训练历史")
public class TrainHistoryBo {
    @ApiModelProperty("训练模板id")
    private String businessTemplateId;

    @ApiModelProperty("训练时间")
    private Date trainTime;

    @ApiModelProperty("训练分值")
    private Double score;
}
