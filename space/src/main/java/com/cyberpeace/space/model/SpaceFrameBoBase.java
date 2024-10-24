package com.cyberpeace.space.model;

import lombok.Data;

@Data
public abstract class SpaceFrameBoBase {

	private String resourceId;

	public abstract SpaceFrameBaseTable bo2Entity();
}
