package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LevelAddInput implements Serializable {

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String parent;

}
