package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 靶场模板公共信息类
 * */
@Data
public class BusinessTemplateRsp implements Serializable {

	@ApiModelProperty(value = "冗余字段，resource_id")
	private String id;

	@ApiModelProperty(value = "唯一标识符")
	private String resourceId;

	@ApiModelProperty(value = "拓扑id，前端兼容")
	private String sceneManagerId;

	@ApiModelProperty(value = "模板名称")
	private String name;

	@ApiModelProperty(value = "模板描述")
	private String description;

	@ApiModelProperty(value = "训练类型")
	private Integer type;

	@ApiModelProperty(value = "训练难度")
	private Integer difficult;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建人名字")
	private String createUser;

	@ApiModelProperty(value = "授权操作类型")
	private Integer share;

	@ApiModelProperty(value = "授权操作用户列表")
	private List<String> shareTeachers;

	@ApiModelProperty(value = "场景编排")
	private SceneConfigRsp sceneConfigInfo;

	@ApiModelProperty(value = "冗余字段，场景类型(日常练习)")
	private Integer sceneType;

}
