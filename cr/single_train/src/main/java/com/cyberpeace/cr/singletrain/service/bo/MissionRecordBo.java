package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务训练记录
 */
@Data
public class MissionRecordBo implements Serializable {
    private String resourceId;

    @ApiModelProperty("任务标题")
    private String title;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("训练记录")
    private JSONObject trainingRecord;
}
