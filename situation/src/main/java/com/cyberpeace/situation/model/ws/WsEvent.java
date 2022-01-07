package com.cyberpeace.situation.model.ws;

import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.LineStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WsEvent implements Serializable {

    @ApiModelProperty("场景id")
    private LineStyle lineStyle;

    @ApiModelProperty("源节点信息（更新部分）")
    private String sourceId;

    @ApiModelProperty("源节点信息（更新部分）")
    private String targetId;

    @ApiModelProperty("源节点信息（更新部分）")
    private Integer attackType;

}
