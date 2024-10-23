package com.cyberpeace.space.model;

import lombok.Data;

@Data
public abstract class SpaceBoBase {

	private String resourceId;

	public abstract SpaceBaseTable bo2Entity();
}
