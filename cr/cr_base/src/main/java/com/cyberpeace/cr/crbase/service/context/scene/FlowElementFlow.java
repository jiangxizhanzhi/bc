package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowElementFlow implements Serializable {

	private String desc;

	private String id;

	private String name;

	private String type;

	private Integer status;

	private String statusText;

	private String taskId;

	private Integer count;

	private Integer interactiveType;

	private Integer category;

	private String sourceIp;

}
