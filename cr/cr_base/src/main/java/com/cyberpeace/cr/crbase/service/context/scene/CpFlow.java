package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class CpFlow implements Serializable {

	private Map<String, ElementFlowList> elements;

}
