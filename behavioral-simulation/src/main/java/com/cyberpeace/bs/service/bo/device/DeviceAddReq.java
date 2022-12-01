package com.cyberpeace.bs.service.bo.device;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class DeviceAddReq implements Serializable {

    @ApiModelProperty(value = "设备id", required = true)
    private String deviceId;

    @ApiModelProperty(value = "设备名称", required = false)
    private String deviceName;

    @ApiModelProperty(value = "设备类型(1:Windows;2:Linux;3:Mac;4:iOS;5:Android)", required = true)
    private Integer deviceType;

    @ApiModelProperty(value = "端口号", required = true)
    private String port;

    public void validate(){
        if(StringUtils.isBlank(deviceId)){
            throw new BusinessException(BsErrorCode.INVALID_DEVICE_ID);
        }
        if(deviceType == null || deviceType < 1 || deviceType > 5){
            throw new BusinessException(BsErrorCode.INVALID_DEVICE_TYPE);
        }
        if(StringUtils.isBlank(port)){
            throw new BusinessException(BsErrorCode.INVALID_PORT);
        }
    }
}
