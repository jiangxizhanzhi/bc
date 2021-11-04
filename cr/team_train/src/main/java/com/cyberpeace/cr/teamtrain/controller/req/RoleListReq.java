package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RoleListReq implements Serializable {

	/**
	 *
	 */
	private String Id;

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
	private Integer roleGroup;

	/**
	 *
	 */
	private List missionsScheme;

	/**
	 *
	 */
	private List nodes;

}
