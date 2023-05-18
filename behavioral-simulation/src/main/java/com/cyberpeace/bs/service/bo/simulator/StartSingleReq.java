package com.cyberpeace.bs.service.bo.simulator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 启动仪表包含的单个行为请求体
 *
 * @author SN
 * @date 2023/05/18
 */
@Data
public class StartSingleReq {
    @ApiModelProperty(value = "仪表id")
    private String simulatorId;

    @ApiModelProperty(value = "用户模型id")
    private String userModelId;

    @ApiModelProperty(value = "行为id")
    private String behaviorId;
}
