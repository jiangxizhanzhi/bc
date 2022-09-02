package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单兵训练记录
 */
@Data
public class TrainRecordBo implements Serializable {

    @ApiModelProperty("阶段")
    private String stage;

    @ApiModelProperty("训练任务记录")
    private List<MissionRecordBo> missions;

}
