package com.cyberpeace.space.service.context.frame;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 动作框架层级
 *
 * @author SN
 * @date 2024/11/05
 */
@Data
@NoArgsConstructor
public class SpaceActionFrameLevel {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 子级
	 */
	private List<SpaceActionFrameLevel> children;

	public SpaceActionFrameLevel(String resourceId, String name){
		this.resourceId = resourceId;
		this.name = name;
		this.children = Lists.newArrayList();
	}

}
