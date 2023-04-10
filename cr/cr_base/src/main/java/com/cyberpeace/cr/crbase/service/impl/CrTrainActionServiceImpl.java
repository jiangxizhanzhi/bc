package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTrainAction;
import com.cyberpeace.cr.crbase.dao.mapper.CrTrainActionMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTrainActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CrTrainActionServiceImpl extends ServiceImpl<CrTrainActionMapper, CrTrainAction> implements CrTrainActionService {

    @Autowired
    private CrRoleService crRoleService;

    @Override
    public Boolean saveTeamAction(String roomId, String content, Integer moduleEvent) {
        return saveAction(roomId, content, null, moduleEvent, CrTrainAction.Module.TEAM.getCode());
    }

    @Override
    public Boolean saveTeamAction(String roomId, String content, Integer moduleEvent, Integer type) {
        return saveAction(roomId, content, type, moduleEvent, CrTrainAction.Module.TEAM.getCode());
    }

    @Override
    public Boolean savePlatformAction(String roomId, String content, Integer moduleEvent) {
        return saveAction(roomId, content, null, moduleEvent, CrTrainAction.Module.PLATFORM.getCode());
    }

    private Boolean saveAction(String roomId, String content, Integer type, Integer moduleEvent, Integer module) {
        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);

        content = String.format("%s 【%s】 ", crRole.getName(), UserInfoContext.getUserContext().getName()) + content;

        CrTrainAction crTrainAction = new CrTrainAction();

        crTrainAction.setRoomId(roomId);
        crTrainAction.setUserId(userId);
        if (type == null) {
            type = CrTrainAction.Type.NORMAL.getCode();
        }
        crTrainAction.setType(type);
        crTrainAction.setRoleId(crRole.getResourceId());
        crTrainAction.setRoleType(crRole.getRoleGroup());
        crTrainAction.setContent(content);
        crTrainAction.setModule(module);
        crTrainAction.setModuleEvent(moduleEvent);
        crTrainAction.setActionTime(new Date());

        return save(crTrainAction);
    }


}
