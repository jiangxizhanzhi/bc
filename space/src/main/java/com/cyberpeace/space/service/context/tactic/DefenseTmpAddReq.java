package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DefenseTmpAddReq extends DefenseAddReq{

    @ApiModelProperty("目标资产")
    private List<NodeAddReq> nodes;

    private String rvbId;

    private String userId;

    @ApiModelProperty("id")
    private String resourceId;
}
