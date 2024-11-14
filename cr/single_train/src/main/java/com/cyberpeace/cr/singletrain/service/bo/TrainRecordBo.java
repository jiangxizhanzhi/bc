package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单兵训练记录
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TrainRecordBo implements Serializable {

	/**
	 * 阶段
	 */
	@ApiModelProperty("阶段")
    private String stage;

	/**
	 * 训练任务记录
	 */
	@ApiModelProperty("训练任务记录")
    private List<MissionRecordBo> missions;

}
