package com.cyberpeace.cr.singletrain.service.bo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练历史Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("训练历史")
public class TrainHistoryBo {
	/**
	 * 训练房间id
	 */
	@ApiModelProperty("训练房间id")
    private String roomId;

	/**
	 * 训练时间
	 */
	@ApiModelProperty("训练时间")
    private Date trainTime;

	/**
	 * 训练分值
	 */
	@ApiModelProperty("训练分值")
    private Double score;

	/**
	 * 训练时间倒序索引
	 */
	@ApiModelProperty("训练时间倒序索引")
    private Integer trainTimes;
}
