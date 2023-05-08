package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.device.DeviceAddReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;

/**
 * 实体设备Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface DeviceService {
    /**
     * 分配实体设备
     *
     * @param req 请求体
     * @return {@code DeviceDistributionRes}
     */
    DeviceDistributionRes distribute(DeviceDistributionReq req);

    /**
     * 新增设备
     *
     * @param req 请求体
     */
    void addDevice(DeviceAddReq req);
}
