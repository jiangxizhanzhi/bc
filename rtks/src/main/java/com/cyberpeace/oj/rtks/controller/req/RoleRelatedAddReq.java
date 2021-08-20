package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleRelatedAddReq implements Serializable {

	@ApiModelProperty(value = "岗位id")
	@NotBlank
	private String role_id;

	@ApiModelProperty(value = "关联记录id列表")
	@NotNull
	private List<String> related_ids;

}
