package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SceneRsp implements Serializable {

	private String id;

	private String createUser;

	private String createUsername;

	private List roles;

	private Integer type;

	private Integer referenceType;

	private String referenceMetaData;

	private String logo;

	private Object sceneConfig;

	private String testScenes;

	private String appScenes;
}
