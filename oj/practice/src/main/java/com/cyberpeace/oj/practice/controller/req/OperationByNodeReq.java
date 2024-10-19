package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OperationByNodeReq implements Serializable {

    @ApiModelProperty("镜像id列表")
    private List<String> nodeIds;
}
