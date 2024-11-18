package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 训练历史博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@ApiModel("训练历史")
public class TrainHistoryBo {
    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("训练房间id")
    private String roomId;

    /**
     * 训练时间
     *
     * @see Date
     */
    @ApiModelProperty("训练时间")
    private Date trainTime;

    /**
     * 得分
     *
     * @see Double
     */
    @ApiModelProperty("训练分值")
    private Double score;

    /**
     * 训练次
     *
     * @see Integer
     */
    @ApiModelProperty("训练时间倒序索引")
    private Integer trainTimes;
}
