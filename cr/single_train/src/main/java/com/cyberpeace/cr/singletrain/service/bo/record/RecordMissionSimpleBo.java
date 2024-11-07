package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录任务简单 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RecordMissionSimpleBo implements Serializable {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;

	/**
	 * 选择
	 */
	@ApiModelProperty(value = "选项")
	private List<JSONObject> option;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "任务答案")
	private String answer;

	/**
	 * 用户答案
	 */
	@ApiModelProperty(value = "用户答案")
	private String userAnswer;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "任务类型")
	private Integer type;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "答题状态，11：未作答，1：成功，2：失败")
	private Integer status;

}
