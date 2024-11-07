package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 训练历史博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("训练历史")
public class TrainHistoryBo {
    /**
     * 房间id
     */
    @ApiModelProperty("训练房间id")
    private String roomId;

    /**
     * 训练时间
     */
    @ApiModelProperty("训练时间")
    private Date trainTime;

    /**
     * 得分
     */
    @ApiModelProperty("训练分值")
    private Double score;

    /**
     * 训练次
     */
    @ApiModelProperty("训练时间倒序索引")
    private Integer trainTimes;
}
