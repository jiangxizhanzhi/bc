package com.cyberpeace.space.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间框架Bo基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class SpaceFrameBoBase {

	private String resourceId;

	public abstract SpaceFrameBaseTable bo2Entity();
}
