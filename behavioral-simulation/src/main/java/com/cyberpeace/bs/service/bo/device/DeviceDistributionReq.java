package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 请求分配实体设备的请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DeviceDistributionReq implements Serializable {
    /**
     * 场景id
     */
    @ApiModelProperty(value = "场景id")
    private String sceneId;

    /**
     * 场景名称
     */
    @ApiModelProperty(value = "场景名称")
    private String sceneName;

    /**
     * 分配实体设备类型和数量
     */
    @ApiModelProperty(value = "分配实体设备类型和数量")
    private List<DeviceTypeNum> request;
}
