package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Topology implements Serializable {

	private List<TopoElement> elements;
}
