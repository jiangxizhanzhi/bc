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

    @ApiModelProperty(value = "销毁时间,单位：小时", name = "destroy_time")
    private Integer destroyTime;

    @ApiModelProperty(value = "实例类型,0=课程，1=实操，2=运维， 3=考试", name = "instance_type")
    private Integer instanceType;

    @ApiModelProperty(value = "日志对象名称", name = "target_name")
    private String targetName;
}
