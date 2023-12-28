package com.cyberpeace.scene.job;

import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.bo.Topology;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/27 11:02
 * @desc :
 **/
public class SceneExecutor implements Runnable, Serializable {

    private SceneJob sceneJob;

    private Topology topology;

    private SceneEntity scene;

    public SceneExecutor(SceneJob sceneJob, Topology topology, SceneEntity scene) {
        this.sceneJob = sceneJob;
        this.topology = topology;
        this.scene = scene;
    }

    @Override
    public void run() {
        sceneJob.start(topology, scene);
    }
}
