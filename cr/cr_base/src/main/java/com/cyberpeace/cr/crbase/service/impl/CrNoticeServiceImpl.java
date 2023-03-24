package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.cr.crbase.controller.rsp.CrNoticeGroupRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrNoticeService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CrNoticeServiceImpl implements CrNoticeService {

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private CrRoleService crRoleService;

    @Override
    public List<CrNoticeGroupRsp> noticeGroup(String roomId) {
        List<CrNoticeGroupRsp> crNoticeGroupRspList = Lists.newArrayList();

        LocalScene localScene = localSceneService.lambdaQuery().eq(LocalScene::getBusinessInstanceId, roomId).one();
        if (localScene == null) {
            return crNoticeGroupRspList;
        }

        if (localScene.getBusinessCode().equals(LocalScene.BusinessCode.RED_VS_BLUE.getCode())) {
            crNoticeGroupRspList.add(CrNoticeGroupRsp.builder().value(CrNotice.Group.ALL.getCode()).build());
            crNoticeGroupRspList.add(CrNoticeGroupRsp.builder().value(CrNotice.Group.RED.getCode()).build());
            crNoticeGroupRspList.add(CrNoticeGroupRsp.builder().value(CrNotice.Group.BLUE.getCode()).build());

        } else if (localScene.getBusinessCode().equals(LocalScene.BusinessCode.TEAM_TRAINING.getCode())) {
            crNoticeGroupRspList.add(CrNoticeGroupRsp.builder().value(CrNotice.Group.ALL.getCode()).build());
            crRoleService.getRolesByRoom(
                    localScene.getBusinessInstanceId(),
                    BusinessTemplate.BusinessType.TRAINING_TEAM.getCode()
            ).stream()
                    .filter(CrRole::isRedStudent)
                    .forEach(crRole -> crNoticeGroupRspList.add(CrNoticeGroupRsp.builder()
                            .value(crRole.getResourceId())
                            .label(crRole.getName())
                            .build()));
        }

        return crNoticeGroupRspList;
    }
}
