package com.cyberpeace.space.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SpaceFrameBoBase {

	private String resourceId;

	public abstract SpaceFrameBaseTable bo2Entity();
}
