package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 场景节点请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class SceneNodeReq implements Serializable {

    /**
     * 资源编号 课时、习题、课堂
     */
    @ApiModelProperty("资源编号 课时、习题、课堂")
    @NotBlank
    private String resourceId;

    /**
     * 节点编号
     */
    @ApiModelProperty("节点编号")
    @NotBlank
    private String nodeId;

    /**
     * 课程表编号
     */
    @ApiModelProperty("课程表编号")
    private String roomId;

}
