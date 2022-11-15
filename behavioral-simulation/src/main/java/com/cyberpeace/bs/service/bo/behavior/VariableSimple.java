package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import lombok.Data;

@Data
public class VariableSimple implements Serializable {

	private String id;

	private String name;

	private String value;
}
