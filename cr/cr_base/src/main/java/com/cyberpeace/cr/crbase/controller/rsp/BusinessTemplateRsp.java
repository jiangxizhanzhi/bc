package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 靶场模板公共信息类
 * */
@Data
public class BusinessTemplateRsp implements Serializable {

	private String id;

	private String resourceId;

	private String sceneManagerId;

	private String name;

	private String description;

	private Integer type;

	private Integer difficult;

	private Date createTime;

	private String createUser;

	private Integer share;

	private List<String> shareTeachers;

	private SceneConfigRsp sceneConfigInfo;
}
