package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceDistributionReq implements Serializable {
    @ApiModelProperty(value = "场景id")
    private String sceneId;

    @ApiModelProperty(value = "场景名称")
    private String sceneName;

    @ApiModelProperty(value = "分配实体设备类型和数量")
    private List<DeviceTypeNum> request;
}
