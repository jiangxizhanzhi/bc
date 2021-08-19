package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class RoleRelatedAddReq implements Serializable {

	@NotBlank
	private String role_id;

	@NotNull
	private List<String> related_ids;

}
