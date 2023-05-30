package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

import java.util.List;

public interface RbCommonDeviceService extends IService<RbCommonDevice> {

    List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

    void addToCollect(RbCommonDeviceContext commonDeviceContext);

    void delCollect(String resourceId);
}
