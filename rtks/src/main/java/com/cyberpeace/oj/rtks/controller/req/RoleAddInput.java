package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RoleAddInput implements Serializable {

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String levelId;

	@NotNull
	private int category;

}
