package com.cyberpeace.integratation.model.situation;

import lombok.Data;

import java.io.Serializable;

@Data
public class SituationSubject implements Serializable {

	private String name;

	private String resourceId;

	private String taskId;
}
