package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * RTKS 角色资源 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RtksRoleResourceBo implements Serializable {

	/**
	 * 中文
	 */
	private List<RtksRoleResource> cn;

	/**
	 * 中文
	 */
	private List<RtksRoleResource> en;
}
