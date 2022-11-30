package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class SceneNodeReq implements Serializable {

    @ApiModelProperty("资源编号 课时、习题、课堂")
    @NotBlank
    private String resourceId;

    @ApiModelProperty("节点编号")
    @NotBlank
    private String nodeId;

    @ApiModelProperty("课程表编号")
    private String roomId;

}
