package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EventScreenContext implements Serializable {

	private List<String> defenseTypes;

	private String startTime;

	private String endTime;

	private List<String> eventName;

	private List<String> sourceNodeIds;

	private List<String> targetNodeIds;
}
