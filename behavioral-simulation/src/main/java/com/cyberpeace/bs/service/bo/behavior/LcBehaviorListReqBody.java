package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcBehaviorListReqBody implements Serializable {

	private Integer groupId = 0;

	private String name;

	private String projectId;

	private Integer page;

	private Integer size;

	private String userName;
}
