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

    @ApiModelProperty("节点名称")
    private String nodeName;

    @ApiModelProperty("事件名称")
    private String eventName;
}
