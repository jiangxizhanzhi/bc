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

	private static Set<String> cache = Sets.newHashSet();

	static {
		cache.add(CR_SCENE);
	}

	public static boolean validPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return cache.contains(path);
	}
}
