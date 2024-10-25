package com.cyberpeace.space.service.context.frame;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Data
public class SpaceActionFrameLevel {

	private String resourceId;

	private String name;

	private List<SpaceActionFrameLevel> children;

	public SpaceActionFrameLevel(String resourceId, String name){
		this.resourceId = resourceId;
		this.name = name;
		this.children = Lists.newArrayList();
	}

}
