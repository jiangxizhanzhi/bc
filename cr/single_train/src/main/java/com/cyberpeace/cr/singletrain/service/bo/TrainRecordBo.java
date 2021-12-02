package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainRecordBo implements Serializable {

    @ApiModelProperty("阶段")
    private String stage;

    @ApiModelProperty("训练任务记录")
    private List<MissionRecordBo> missions;

}
