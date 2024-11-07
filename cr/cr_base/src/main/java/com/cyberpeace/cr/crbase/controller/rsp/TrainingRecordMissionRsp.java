package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.service.context.TrainingRecord;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 在获取训练记录接口使用
 *
 * @author 张晋宝
 * @date 2024/11/07
 */
@Data
public class TrainingRecordMissionRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 训练记录
	 */
	@ApiModelProperty("训练记录")
	private TrainingRecord trainingRecord;

	/**
	 * 培训状态
	 */
	@ApiModelProperty("训练状态")
	private Integer traininStatus;
}
