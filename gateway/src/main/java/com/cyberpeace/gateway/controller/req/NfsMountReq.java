package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NfsMountReq implements Serializable {

	@NotBlank
	private String host;

	@NotBlank
	private String path;

}
