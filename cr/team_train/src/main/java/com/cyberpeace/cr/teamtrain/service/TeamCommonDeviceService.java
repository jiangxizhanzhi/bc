package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

import java.util.List;

public interface TeamCommonDeviceService extends IService<TeamCommonDevice> {

    List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

    void addToCollect(TeamCommonDeviceContext commonDeviceContext);

    void delCollect(String resourceId);
}
