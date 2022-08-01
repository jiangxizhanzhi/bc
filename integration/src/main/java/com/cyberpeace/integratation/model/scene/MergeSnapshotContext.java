package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class MergeSnapshotContext implements Serializable {

	private String imageType;

	private String newName;

	private String snapshotName;

	private String uuid;

}
