package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 实体设备分配结果
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DeviceDistributionRes implements Serializable {
    /**
     * 分配成功与否
     */
    @ApiModelProperty(value = "分配成功与否")
    private Boolean success = false;

    /**
     * 消息
     */
    @ApiModelProperty(value = "消息")
    private String message = "";

    /**
     * 分配的设备
     */
    @ApiModelProperty(value = "分配的设备")
    private List<DeviceTypePort> device = Lists.newArrayList();

    /**
     * 添加选中的设备id
     *
     * @param type        类型
     * @param selectedIds 选中的id
     */
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
