package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 重命名拓扑上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RenameTopoContext implements Serializable {

	private String id;

	private String name;

	/**
	 * 重命名拓扑上下文
	 *
	 * @param topoId  拓扑id
	 * @param newName 新名字
	 */
	public RenameTopoContext(String topoId, String newName) {
		this.id = topoId;
		this.name = newName;
	}
}
