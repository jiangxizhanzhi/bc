package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CollectionEventPageContext implements Serializable {
    private String sceneId;

    private List<String> nodeIds;

    // todo 通用探针无id, 作为type
    private List<String> probeId;

    private Date startTime;

    private Date endTime;

    private Long page;

    private Long pageSize;
}
