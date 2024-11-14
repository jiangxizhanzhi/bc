package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单任务Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练任务信息")
public class SingleMissionBo implements Serializable {
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
	 * 任务分类
	 */
	@ApiModelProperty("任务分类")
    private String category;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
    private Integer type;

	/**
	 * 选项
	 */
	@ApiModelProperty("选项")
    private List<JSONObject> option;

	/**
	 * 任务记录
	 */
	@ApiModelProperty("任务记录")
    private JSONObject trainingRecord;

	/**
	 * 题解
	 */
	@ApiModelProperty(value = "题解")
	private String writeUp;
}
