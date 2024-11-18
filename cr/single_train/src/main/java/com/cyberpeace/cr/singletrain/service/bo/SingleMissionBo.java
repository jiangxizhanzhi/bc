package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单任务 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("单兵训练任务信息")
public class SingleMissionBo implements Serializable {
    /**
     * 标题
     *
     * @see String
     */
    @ApiModelProperty("任务标题")
    private String title;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("任务描述")
    private String description;

    /**
     * 类别
     *
     * @see String
     */
    @ApiModelProperty("任务分类")
    private String category;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("题型")
    private Integer type;

    /**
     * 选择
     *
     * @see List<JSONObject>
     */
    @ApiModelProperty("选项")
    private List<JSONObject> option;

    /**
     * 训练记录
     *
     * @see JSONObject
     */
    @ApiModelProperty("任务记录")
    private JSONObject trainingRecord;

    /**
     * 写入向上
     *
     * @see String
     */
    @ApiModelProperty(value = "题解")
	private String writeUp;
}
