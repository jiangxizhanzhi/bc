package com.cyberpeace.scene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.scene.dao.entity.SceneEntity;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
public interface SceneService extends IService<SceneEntity> {

    SceneEntity startScene(String topologyId, String instanceId);
}
