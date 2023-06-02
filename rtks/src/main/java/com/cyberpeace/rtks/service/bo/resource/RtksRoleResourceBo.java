package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RtksRoleResourceBo implements Serializable {

	private List<RtksRoleResource> cn;

	private List<RtksRoleResource> en;
}
