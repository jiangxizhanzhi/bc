package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrTrainAction;

public interface CrTrainActionService extends IService<CrTrainAction> {
    Boolean saveTeamAction(String roomId, String content, Integer type);

    Boolean savePlatformAction(String roomId, String content, Integer type);
}
