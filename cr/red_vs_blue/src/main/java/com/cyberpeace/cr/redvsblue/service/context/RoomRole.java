package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoomRole {

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> users;

}