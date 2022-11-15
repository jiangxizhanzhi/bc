package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class VariableModifyReq implements Serializable {

	private String behaviorId;

	private List<VariableSimple> variables;
}
