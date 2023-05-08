package com.cyberpeace.bs.service.bo.simulator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 场景id与名称
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class SceneIdName implements Serializable {
    /**
     * 场景id
     */
    @ApiModelProperty(value = "试验任务id")
    private String sceneId;

    /**
     * 场景名称
     */
    @ApiModelProperty(value = "试验任务名称")
    private String sceneName;
}
