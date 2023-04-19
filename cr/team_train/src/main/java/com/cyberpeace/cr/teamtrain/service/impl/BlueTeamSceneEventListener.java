package com.cyberpeace.cr.teamtrain.service.impl;

import com.cyberpeace.cs.dao.entity.LocalScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlueTeamSceneEventListener extends TeamSceneEventListener {
    @Override
    public Integer declareMessageType() {
        return LocalScene.BusinessCode.BLUE_TEAM_TRAINING.getCode();
    }
}
