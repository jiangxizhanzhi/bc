package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * RTKS岗位资源Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RtksRoleResourceBo implements Serializable {

	/**
	 * 中文
	 *
	 * @see List<RtksRoleResource>
	 */
	private List<RtksRoleResource> cn;

	/**
	 * 英文
	 *
	 * @see List<RtksRoleResource>
	 */
	private List<RtksRoleResource> en;
}
