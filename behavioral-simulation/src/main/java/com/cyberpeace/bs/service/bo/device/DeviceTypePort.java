package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceTypePort implements Serializable {
    @ApiModelProperty(value = "实体设备类型")
    private Integer deviceType;

    @ApiModelProperty(value = "分配设备列表")
    private List<DevicePortId> deviceList = Lists.newArrayList();

    public void addDeviceId(String id){
        DevicePortId portId = new DevicePortId();
        portId.setId(id);
        deviceList.add(portId);
    }
}
