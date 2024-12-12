package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 场景节点请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class SceneNodeReq implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 课时、习题、课堂")
    @NotBlank
    private String resourceId;

    /**
     * 节点id
     *
     * @see String
     */
    @ApiModelProperty("节点编号")
    @NotBlank
    private String nodeId;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("课程表编号")
    private String roomId;

}
