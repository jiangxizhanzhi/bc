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
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练任务信息")
public class SingleMissionBo implements Serializable {
    /**
     * 标题
     */
    @ApiModelProperty("任务标题")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty("任务描述")
    private String description;

    /**
     * 类别
     */
    @ApiModelProperty("任务分类")
    private String category;

    /**
     * 类型
     */
    @ApiModelProperty("题型")
    private Integer type;

    /**
     * 选择
     */
    @ApiModelProperty("选项")
    private List<JSONObject> option;

    /**
     * 训练记录
     */
    @ApiModelProperty("任务记录")
    private JSONObject trainingRecord;

    /**
     * 写入向上
     */
    @ApiModelProperty(value = "题解")
	private String writeUp;
}
