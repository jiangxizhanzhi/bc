package com.cyberpeace.cs.service.context.scene;

import lombok.Data;

@Data
public class SceneCollectionImpl implements SceneCollection {

    private Boolean enable_flow_collect = false;

    private Boolean enable_probe_collect;
}
