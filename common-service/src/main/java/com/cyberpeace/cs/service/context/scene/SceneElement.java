package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SceneElement implements Serializable {

	private String id;

	private String name;

	private List<PortImpl> ports;
}
