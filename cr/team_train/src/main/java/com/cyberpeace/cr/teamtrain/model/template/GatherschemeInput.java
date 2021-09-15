package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;

import lombok.Data;

@Data
public class GatherschemeInput implements Serializable {

	/**
	 * 
	 */
	private Object scheme;

	/**
	 * 
	 */
	private Boolean tapFlow;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

}