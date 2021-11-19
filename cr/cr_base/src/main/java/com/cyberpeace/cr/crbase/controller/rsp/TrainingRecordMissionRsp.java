package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.TrainingRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 在获取训练记录接口使用
 * @author 张晋宝
 */
@Data
public class TrainingRecordMissionRsp implements Serializable {

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("任务名称")
	private String title;

	@ApiModelProperty("任务描述")
	private String description;

	@ApiModelProperty("训练记录")
	private TrainingRecord trainingRecord;

}
