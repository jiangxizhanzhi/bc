package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlowElement implements Serializable {

	private List<FlowElementFlow> flows;
}
