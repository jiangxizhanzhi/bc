package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;

public interface LocalSceneService extends IService<LocalScene> {

    LocalScene getByBusinessInstanceId(String businessInstanceId);

    String startScene(String topoId);
}
