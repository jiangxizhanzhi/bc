package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsSingleNodeEffect implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("消息编码")
    private String code;

    @ApiModelProperty("动效名称")
    private String name;

    @ApiModelProperty("节点信息（更新部分）")
    private ElementInfo elementInfo;

}
