package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * RTKS资源请求体
 *
 * @author SN
 * @date 2024/11/07
 */
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
