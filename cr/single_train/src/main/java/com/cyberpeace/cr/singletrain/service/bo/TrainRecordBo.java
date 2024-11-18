package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单兵训练记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainRecordBo implements Serializable {

    /**
     * 阶段
     *
     * @see String
     */
    @ApiModelProperty("阶段")
    private String stage;

    /**
     * 任务
     *
     * @see List<MissionRecordBo>
     */
    @ApiModelProperty("训练任务记录")
    private List<MissionRecordBo> missions;

}
