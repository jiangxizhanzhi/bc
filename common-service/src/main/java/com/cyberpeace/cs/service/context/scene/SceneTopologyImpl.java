package com.cyberpeace.cs.service.context.scene;

import lombok.Data;

import java.util.List;

@Data
public class SceneTopologyImpl implements SceneTopology {

    private SceneStatusImpl scene;

    private List<SceneElement> elements;

}
