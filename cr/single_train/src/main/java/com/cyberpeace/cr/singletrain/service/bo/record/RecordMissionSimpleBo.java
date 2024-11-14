package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录任务简单Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RecordMissionSimpleBo implements Serializable {

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
	private String title;

    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述")
	private String description;

    /**
     * 选项
     */
    @ApiModelProperty(value = "选项")
	private List<JSONObject> option;

    /**
     * 任务答案
     */
    @ApiModelProperty(value = "任务答案")
	private String answer;

    /**
     * 用户答案
     */
    @ApiModelProperty(value = "用户答案")
	private String userAnswer;

    /**
     * 任务类型
     */
    @ApiModelProperty(value = "任务类型")
	private Integer type;

    /**
     * 答题状态，11：未作答，1：成功，2：失败
     */
    @ApiModelProperty(value = "答题状态，11：未作答，1：成功，2：失败")
	private Integer status;

}
