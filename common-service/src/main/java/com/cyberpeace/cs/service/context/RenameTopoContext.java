package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class RenameTopoContext implements Serializable {

	private String id;

	private String name;

	public RenameTopoContext(String topoId, String newName) {
		this.id = topoId;
		this.name = newName;
	}
}
