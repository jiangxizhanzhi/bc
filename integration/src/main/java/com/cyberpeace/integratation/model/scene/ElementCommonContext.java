package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ElementCommonContext implements Serializable {

	private String sceneId;

	private List<String> elementIds;

}
