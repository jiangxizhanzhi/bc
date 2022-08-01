package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SceneTopology implements Serializable {

	private SceneStatus scene;

	private List<SceneElement> elements;

}
