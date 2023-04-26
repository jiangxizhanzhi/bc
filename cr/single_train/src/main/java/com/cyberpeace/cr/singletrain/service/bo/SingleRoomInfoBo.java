package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练房间信息")
public class SingleRoomInfoBo implements Serializable {

	@ApiModelProperty("roomId")
	private String resourceId;

	@ApiModelProperty("训练开始时间")
	private Date startTime;

	@ApiModelProperty("训练结束时间")
	private Date endTime;

	@ApiModelProperty("训练记录")
	private JSONObject trainingRecordData;

	@ApiModelProperty("运行时间, 单位/秒")
	private Long runningTime;
}
