package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.device.DeviceDistributionReq;
import com.cyberpeace.bs.service.bo.device.DeviceDistributionRes;

public interface DeviceDistributionService {
    DeviceDistributionRes distribute(DeviceDistributionReq req);
}
