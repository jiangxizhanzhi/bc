package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单人房信息 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练房间信息")
public class SingleRoomInfoBo implements Serializable {

	/**
	 * 开始时间
	 */
	@ApiModelProperty("训练开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("训练结束时间")
	private Date endTime;

	/**
	 * 训练记录数据
	 */
	@ApiModelProperty("训练记录")
	private JSONObject trainingRecordData;

	/**
	 * 运行时间
	 */
	@ApiModelProperty("运行时间, 单位/秒")
	private Long runningTime;
}
