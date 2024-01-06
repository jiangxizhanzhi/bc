package com.cyberpeace.scene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.dao.entity.SceneEntity;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
public interface SceneService extends IService<SceneEntity> {

    BaseRsp startScene(SceneStartReq req);

    void verify();

    BaseRsp destroyScene(String sceneId);

    BaseRsp detail(String topologyId, String instanceId);

    BaseRsp console(String nodeId, Integer type);

    void repair(String filePath);

    void autoDestroy();
}
