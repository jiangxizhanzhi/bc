package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务训练记录
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MissionRecordBo implements Serializable {
	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 任务标题
	 */
	@ApiModelProperty("任务标题")
    private String title;

	/**
	 * 任务描述
	 */
	@ApiModelProperty("任务描述")
    private String description;

	/**
	 * 训练记录
	 */
	@ApiModelProperty("训练记录")
    private JSONObject trainingRecord;
}
