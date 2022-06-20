package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NfsUmountReq implements Serializable {

	@NotBlank
	private String path;

}
