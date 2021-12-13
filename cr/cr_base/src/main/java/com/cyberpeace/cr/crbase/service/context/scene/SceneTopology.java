package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SceneTopology implements Serializable {

	private SceneStatus scene;

	private List<SceneElement> elements;

}
