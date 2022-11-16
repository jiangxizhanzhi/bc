package com.cyberpeace.integratation.model.situation;

import lombok.Data;

import java.io.Serializable;

@Data
public class SituationTask implements Serializable {

	private String name;

	private String resourceId;
}
