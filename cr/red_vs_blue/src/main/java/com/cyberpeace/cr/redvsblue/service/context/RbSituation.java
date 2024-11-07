package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * RB 情况
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbSituation implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 散 列
	 */
	private String hash;
}
