package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScenePlugins implements Serializable {

	private CpFlow cpFlow;

	private CpCollection cpCollection;
}
