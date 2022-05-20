package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class ElementFlow implements Serializable {

	private String id;

	private String name;

	private String desc;

	private String type;

	private Integer category;

	private Integer interactiveType;

	private Integer commonStatus;

	private Integer status;

	private String statusText;

	private String taskId;

	private Integer count;

	private String sourceIp;

}
