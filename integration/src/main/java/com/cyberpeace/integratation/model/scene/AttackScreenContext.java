package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttackScreenContext implements Serializable {

	private String startTime;

	private String endTime;

	private List<String> defenseTypes;

	private List<String> eventName;

	private List<String> sourceNodeIds;

	private List<String> targetNodeIds;
}
