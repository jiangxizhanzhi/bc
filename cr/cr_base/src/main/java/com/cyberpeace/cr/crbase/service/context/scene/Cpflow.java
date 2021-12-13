package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class Cpflow implements Serializable {

	private Map<String, FlowElement> elements;
}
