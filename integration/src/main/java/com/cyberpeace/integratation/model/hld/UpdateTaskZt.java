package com.cyberpeace.integratation.model.hld;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateTaskZt implements Serializable {

	/**
	 * 内容
	 */
	private String id;

	/**
	 * 描述
	 */
	private String sceneId;

}
