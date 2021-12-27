package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbRoomModifyReq implements Serializable {

	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	private String template;

	private Integer category;

	private String name;

	private String preSkill;

	private String trainSkill;

	private Integer difficult;

	private Date startTime;

	private Date endTime;

	private String description;

	private String roleConfig;
}