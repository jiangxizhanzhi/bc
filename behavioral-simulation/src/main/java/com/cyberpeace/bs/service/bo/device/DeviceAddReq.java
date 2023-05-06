package com.cyberpeace.bs.service.bo.device;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 设备添加请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DeviceAddReq implements Serializable {

    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id", required = true)
    private String deviceId;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", required = false)
    private String deviceName;

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型(1:Windows;2:Linux;3:Mac;4:iOS;5:Android)", required = true)
    private Integer deviceType;

    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号", required = true)
    private String port;

    /**
     * 校验请求体
     * 设备id不可为空
     * 设备类型为1~5之间的数
     * 端口号不可为空
     */
    public void validate() {
        if (StringUtils.isBlank(deviceId)) {
            throw new BusinessException(BsErrorCode.INVALID_DEVICE_ID);
        }
        if (deviceType == null || deviceType < 1 || deviceType > 5) {
            throw new BusinessException(BsErrorCode.INVALID_DEVICE_TYPE);
        }
        if (StringUtils.isBlank(port)) {
            throw new BusinessException(BsErrorCode.INVALID_PORT);
        }
    }
}
