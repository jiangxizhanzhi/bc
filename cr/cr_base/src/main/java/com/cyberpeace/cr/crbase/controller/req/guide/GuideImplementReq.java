package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GuideImplementReq implements Serializable {

    @ApiModelProperty(value = "资源id", required = true)
    @NotBlank
    private String resourceId;

    @ApiModelProperty("场景id")
    @NotBlank
    private String sceneId;

    @ApiModelProperty("是否为实例场景")
    private Boolean isInstance;

    @ApiModelProperty("导调方案名称")
    @NotBlank
    private String eventName;

    @ApiModelProperty("动效类型")
    private String type;

    @ApiModelProperty("动效描述")
    private String description;

    @ApiModelProperty("源节点id")
    private String sourceId;

    @ApiModelProperty("目标节点id")
    private String targetId;

}
