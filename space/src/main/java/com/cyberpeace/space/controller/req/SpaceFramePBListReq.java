package com.cyberpeace.space.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceFramePBListReq implements Serializable {
    public SpaceFramePBListReq(Integer type, String name, String categoryId, String rvbId) {
        this.type = type;
        this.name = name;
        this.categoryId = categoryId;
        this.rvbId = rvbId;
    }


    @ApiModelProperty("框架类型")
    private Integer type;

    @ApiModelProperty("名称搜索")
    private String name;

    @ApiModelProperty("目标类型")
    private String categoryId;

    @ApiModelProperty("人机对抗id")
    private String rvbId;
}
