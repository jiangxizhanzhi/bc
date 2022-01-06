package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsMultiNodeEffect implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("消息编码")
    private String code;

    @ApiModelProperty("动效名称")
    private String name;

    @ApiModelProperty("动效类别")
    private String category;

    @ApiModelProperty("动效类型")
    private String type;

    @ApiModelProperty("源节点信息（更新部分）")
    private ElementInfo sourceElementInfo;

    @ApiModelProperty("目标节点信息（更新部分）")
    private ElementInfo destElementInfo;

}
