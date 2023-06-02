package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RtksResourceReq implements Serializable {

	/**
	 * 系统名称
	 */
	private String systemName;

	/**
	 * 系统主题
	 */
	private String systemType;

	/**
	 * 角色
	 */
	private List<RoleResource> roles;

	/**
	 * rtks岗位
	 */
	private RtksRoleResourceBo rtks;
}
