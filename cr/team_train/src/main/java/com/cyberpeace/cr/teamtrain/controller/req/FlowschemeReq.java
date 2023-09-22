package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @TableName cr_team_train_flowscheme_template
 */
@Data
public class FlowschemeReq implements Serializable {

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
	private String scheme;

}