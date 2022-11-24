package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefenseTypeCount implements Serializable {

	private String defenseType;

	private Integer times;
}
