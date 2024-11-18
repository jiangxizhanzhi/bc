package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * RB 情况
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbSituation implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 散 列
	 *
	 * @see String
	 */
	private String hash;
}
