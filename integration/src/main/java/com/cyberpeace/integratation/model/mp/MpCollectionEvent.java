package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

@Data
public class MpCollectionEvent implements Serializable {
    private String content;

    private String time;

    private String probeData;

    private String probeId;

    private String nodeId;

    private String sceneId;
}
