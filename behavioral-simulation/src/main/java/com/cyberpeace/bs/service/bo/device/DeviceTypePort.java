package com.cyberpeace.bs.service.bo.device;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 设备类型和端口号
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class DeviceTypePort implements Serializable {
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "实体设备类型")
    private Integer deviceType;

    /**
     * 分配设备列表
     */
    @ApiModelProperty(value = "分配设备列表")
    private List<DevicePortId> deviceList = Lists.newArrayList();

    /**
     * 添加设备id
     *
     * @param id id
     */
    public void addDeviceId(String id) {
        DevicePortId portId = new DevicePortId();
        portId.setId(id);
        deviceList.add(portId);
    }
}
