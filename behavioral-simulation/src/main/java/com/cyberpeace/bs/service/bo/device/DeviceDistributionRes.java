package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceDistributionRes implements Serializable {
    @ApiModelProperty(value = "分配成功与否")
    private Boolean success = false;

    @ApiModelProperty(value = "消息")
    private String message = "";

    @ApiModelProperty(value = "分配的设备")
    private List<DeviceTypePort> device = Lists.newArrayList();

    public void addSelectedIds(Integer type, List<String> selectedIds) {
        DeviceTypePort typePort = new DeviceTypePort();
        typePort.setDeviceType(type);
        for (String id : selectedIds) {
            typePort.addDeviceId(id);
        }
        device.add(typePort);
    }

    public void addMessage(String msg) {
        this.message = this.message + msg + "\n";
    }
}
