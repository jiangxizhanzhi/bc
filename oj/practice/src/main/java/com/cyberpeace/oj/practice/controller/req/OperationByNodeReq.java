package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实操由节点请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class OperationByNodeReq implements Serializable {

    /**
     * 节点id列表
     */
    @ApiModelProperty("镜像id列表")
    private List<String> nodeIds;
}
