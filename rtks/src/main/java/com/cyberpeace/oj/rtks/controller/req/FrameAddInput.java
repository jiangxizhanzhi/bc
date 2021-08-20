package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FrameAddInput implements Serializable {

	@NotBlank
	private String name;

	private String description;

}
