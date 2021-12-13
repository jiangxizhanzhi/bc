package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SceneElement implements Serializable {

	private String id;

	private String name;

	private List<Port> ports;
}
