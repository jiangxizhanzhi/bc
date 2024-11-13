package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 重命名拓扑上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class RenameTopoContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	public RenameTopoContext(String topoId, String newName) {
		this.id = topoId;
		this.name = newName;
	}
}
