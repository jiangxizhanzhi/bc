package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class GxAccessReq implements Serializable {

	private String token;

	private String roleName;
}
