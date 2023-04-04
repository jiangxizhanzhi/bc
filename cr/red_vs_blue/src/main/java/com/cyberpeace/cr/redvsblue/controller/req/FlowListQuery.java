package com.cyberpeace.cr.redvsblue.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FlowListQuery extends PageReqBase implements Serializable{
    @ApiModelProperty("训练实例id")
    private String roomId;

    @ApiModelProperty("场景节点id")
    private String nodeId;

    @ApiModelProperty("交互类型, 背景流量=0;攻击流量=1")
    private Integer interactiveType;

}
