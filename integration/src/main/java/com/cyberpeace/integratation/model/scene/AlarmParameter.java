package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlarmParameter implements Serializable {

    @ApiModelProperty("节点id")
    private String nodeId;

    @ApiModelProperty("事件名称")
    private String eventName;

    @ApiModelProperty("告警匹配名称")
    private String alarmMatchName;
}
