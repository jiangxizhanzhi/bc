package com.cyberpeace.common.constant;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

/**
 * 对外暴露的websocket路径
 */
public class WsPath {

	private static final String CR_SCENE = "/cr/ce_base/scene";

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
