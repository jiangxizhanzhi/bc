package com.cyberpeace.common.constant;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

/**
 * 对外暴露的websocket路径
 */
public class WsPath {

	/**
	 * 单兵、团队、红蓝的场景启动、节点状态变更，使用此path
	 */
	public static final String CR_SCENE = "/ws/cr/ce_base/scene";

	public static final String CR_MISSION = "/ws/cr/ce_base/mission";

	public static final String OPERATION_SCENE = "/ws/oj/operation/scene";

	public static final String LESSON_SCENE = "/ws/course/lesson_scene";

	public static final String LESSON_PRACTICE_SCENE = "/ws/course/lesson/operation/scene";

	private static Set<String> cache = Sets.newHashSet();

	static {
		cache.add(CR_SCENE);
		cache.add(CR_MISSION);
		cache.add(OPERATION_SCENE);
		cache.add(LESSON_SCENE);
		cache.add(LESSON_PRACTICE_SCENE);
	}

	public static boolean validPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return cache.contains(path);
	}
}
