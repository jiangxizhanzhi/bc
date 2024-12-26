package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageEvent {

    private String mpSceneId;

    private String nodeName;

    private Integer page;

    private Integer perPage;

    @ApiModelProperty("处理数据隔离")
    private Integer quarantine;

    private String nodeId;

    private String alarmName;
}
