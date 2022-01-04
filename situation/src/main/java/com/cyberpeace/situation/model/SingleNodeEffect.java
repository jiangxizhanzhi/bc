package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SingleNodeEffect implements Serializable {

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点id")
    private String elementId;

    @ApiModelProperty("节点信息（更新部分）")
    private ElementInfo elementInfo;

}
