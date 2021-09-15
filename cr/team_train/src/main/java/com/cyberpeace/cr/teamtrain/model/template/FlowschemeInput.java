package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @TableName cr_team_train_flowscheme_template
 */
@Data
public class FlowschemeInput implements Serializable {

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private Object scheme;

}