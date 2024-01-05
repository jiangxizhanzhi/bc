package com.cyberpeace.scene.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   辛发圣
 * @date :   2024/1/5 9:56
 * @desc :
 **/
@Data
@ApiModel
public class SceneStartReq implements Serializable {

    @ApiModelProperty(value = "拓扑id", name = "topology_id")
    private String topologyId;

    @ApiModelProperty(value = "实例id", name = "instance_id")
    private String instanceId;
}
