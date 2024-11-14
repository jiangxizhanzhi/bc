package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 红蓝对抗态势
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbSituation implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * id
	 */
	private String hash;
}
