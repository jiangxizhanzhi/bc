package com.cyberpeace.oj.practice.service.impl;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneHandler;

public class OperationSceneHandler extends SceneHandler {

    @Override
    public Integer getBusinessType() {
        return LocalScene.BusinessCode.PRACTICE.getCode();
    }

    @Override
    public String getReferenceMetaData(LocalScene localScene) {
        return super.getReferenceMetaData(localScene);
    }
}
