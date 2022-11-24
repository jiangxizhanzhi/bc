package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttackTypeCount implements Serializable {

	private String attackType;

	private Integer times;
}
