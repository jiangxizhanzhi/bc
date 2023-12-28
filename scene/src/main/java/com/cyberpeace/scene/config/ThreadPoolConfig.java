package com.cyberpeace.scene.config;

import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.util.SceneThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author :   SN
 * @date :   2023/12/27 10:31
 * @desc :
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    public SceneThreadPoolExecutor threadPoolSceneExecutor() {
        return new SceneThreadPoolExecutor(3, Math.max(Runtime.getRuntime().availableProcessors(),4), 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    }

    @Bean
    public SceneJob startSceneConfig(SceneService sceneService, ContainerService containerService, NetworkService networkService,
                                     HostService hostService, TopologyService topologyService, NodeService nodeService) {
        SceneJob scene = new SceneJob();
        scene.setSceneService(sceneService);
        scene.setContainerService(containerService);
        scene.setHostService(hostService);
        scene.setNetworkService(networkService);
        scene.setTopologyService(topologyService);
        scene.setNodeService(nodeService);
        return scene;
    }
}
