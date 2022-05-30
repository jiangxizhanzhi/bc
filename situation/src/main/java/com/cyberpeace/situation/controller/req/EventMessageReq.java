package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class EventMessageReq implements Serializable {

    @ApiModelProperty("场景id")
    @NotEmpty
    private String sceneId;

    @ApiModelProperty("消息类型")
    private String code;

    @ApiModelProperty("消息内容")
    private WsMultiNodeDynamic data;

}
