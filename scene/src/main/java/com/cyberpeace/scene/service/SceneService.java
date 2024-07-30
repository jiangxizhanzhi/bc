package com.cyberpeace.scene.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.bo.SceneData;
import com.cyberpeace.scene.service.bo.ad.AdSceneStartReq;
import com.cyberpeace.scene.service.bo.res.SceneListRsp;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
public interface SceneService extends IService<SceneEntity> {

    BaseRsp startScene(SceneStartReq req);

    void verify(String userId);

	void destroyScene(String sceneId);

    BaseRsp detail(String topologyId, String instanceId);

    BaseRsp console(String nodeId, Integer type);

    void repair(String filePath);

    void autoDestroy();

    JSONObject getSceneJsonObject(String instanceId);

	JSONObject startSceneByTopo(AdSceneStartReq req);

	SceneData getSceneDataBySceneId(String sceneId);

    List<String> getUndeletedSceneIdByUserId(String userId);

    void destroySceneBatch(List<String> ids);

    /**
     * 竞赛用，关闭考试的全部场景
     *
     * @param raceId
     */
    void destroySceneByRaceId(String raceId);

    ListRsp<SceneListRsp> getSceneList(PageReqBase req, Integer type);
}
