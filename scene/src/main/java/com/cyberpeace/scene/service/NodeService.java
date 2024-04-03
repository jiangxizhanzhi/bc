package com.cyberpeace.scene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.scene.dao.entity.NodeEntity;

/**
 * @author :   SN
 * @date :   2023/12/28 11:20
 * @desc :
 **/
public interface NodeService extends IService<NodeEntity> {

    void delete(String sceneId);

    void clear();
}
