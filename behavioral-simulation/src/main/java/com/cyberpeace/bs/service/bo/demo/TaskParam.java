package com.cyberpeace.bs.service.bo.demo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskParam implements Serializable {

	private String deviceId;

	private String environmentId;

	private String projectId;
}
