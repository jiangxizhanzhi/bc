package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class TopoElement implements Serializable {

	private String id;

	private String name;

	private String systemType;
}
