package com.cyberpeace.scene.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.bo.SceneData;
import com.cyberpeace.scene.service.bo.ad.AdSceneStartReq;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
public interface SceneService extends IService<SceneEntity> {

    BaseRsp startScene(SceneStartReq req);

    void verify(String userId);

    BaseRsp destroyScene(String sceneId);

    BaseRsp detail(String topologyId, String instanceId);

    BaseRsp console(String nodeId, Integer type);

    void repair(String filePath);

    void autoDestroy();

    void sceneState();

    JSONObject getSceneJsonObject(String instanceId);

	SceneEntity startSceneByTopo(AdSceneStartReq req);

	SceneData getSceneDataBySceneId(String sceneId);
}
