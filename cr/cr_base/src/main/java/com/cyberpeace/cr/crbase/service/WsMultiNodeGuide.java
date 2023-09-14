package com.cyberpeace.cr.crbase.service;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsMultiNodeGuide implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("持续时间（秒）")
    private Integer duration;

    @ApiModelProperty("事件名称")
    private String name;

    @ApiModelProperty("攻防类别")
    private Integer category;

    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    private Integer eventType;

    @ApiModelProperty("源节点id")
    private String sourceId;

    @ApiModelProperty("目的节点id")
    private String targetId;

    @ApiModelProperty("攻击结果 1:成功 2失败")
    private Integer result;

    @ApiModelProperty("归属角色 4:红队 5蓝队")
    private Integer role;

}
