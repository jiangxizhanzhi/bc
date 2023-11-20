package com.cyberpeace.cs.service.context.scene;

import lombok.Data;

@Data
public class ScenePluginsImpl implements ScenePlugins {

    private CpflowImpl cpflow;

    private CpcollectionImpl cpcollection;
}
