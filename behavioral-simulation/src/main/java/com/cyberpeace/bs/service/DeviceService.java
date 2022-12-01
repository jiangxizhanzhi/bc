package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.device.DeviceAddReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;

public interface DeviceService {
    DeviceDistributionRes distribute(DeviceDistributionReq req);

    void addDevice(DeviceAddReq req);
}
