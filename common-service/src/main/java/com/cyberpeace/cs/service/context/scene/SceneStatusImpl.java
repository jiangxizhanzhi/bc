package com.cyberpeace.cs.service.context.scene;

import lombok.Data;

@Data
public class SceneStatusImpl implements SceneStatus {

    private Integer event;

    private Integer event_status;
}
