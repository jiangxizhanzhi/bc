package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务训练记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionRecordBo implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

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
     * 训练记录
     *
     * @see JSONObject
     */
    @ApiModelProperty("训练记录")
    private JSONObject trainingRecord;
}
