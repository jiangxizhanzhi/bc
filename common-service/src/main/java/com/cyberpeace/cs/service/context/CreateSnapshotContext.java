package com.cyberpeace.cs.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateSnapshotContext implements Serializable {

    private String nodeId;

    private String snapshotName;

    private String description;

}
