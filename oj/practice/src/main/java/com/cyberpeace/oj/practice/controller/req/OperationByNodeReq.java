package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实操由节点请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class OperationByNodeReq implements Serializable {

    /**
     * 节点id列表
     *
     * @see List<String>
     */
    @ApiModelProperty("镜像id列表")
    private List<String> nodeIds;
}
