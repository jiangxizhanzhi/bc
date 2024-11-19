package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * RTKS资源请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RtksResourceReq implements Serializable {

	/**
	 * 系统名称
	 *
	 * @see String
	 */
	private String systemName;

	/**
	 * 系统主题
	 *
	 * @see String
	 */
	private String systemType;

	/**
	 * 角色
	 *
	 * @see List<RoleResource>
	 */
	private List<RoleResource> roles;

	/**
	 * rtks岗位
	 *
	 * @see RtksRoleResourceBo
	 */
	private RtksRoleResourceBo rtks;
}
