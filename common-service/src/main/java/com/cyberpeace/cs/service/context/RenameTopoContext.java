package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 重命名拓扑上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RenameTopoContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	public RenameTopoContext(String topoId, String newName) {
		this.id = topoId;
		this.name = newName;
	}
}
