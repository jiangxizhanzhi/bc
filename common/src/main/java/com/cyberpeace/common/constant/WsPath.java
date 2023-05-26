package com.cyberpeace.common.constant;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

/**
 * 对外暴露的websocket路径
 */
public class WsPath {

	/**
	 * 单兵、团队、红蓝的场景启动、节点状态变更、态势消息，使用此path
	 */
	public static final String CR_SCENE = "/ws/cr/ce_base/scene";

	public static final String CR_MISSION = "/ws/cr/ce_base/mission";

	public static final String CR_CHAT = "/ws/cr/ce_base/chat";

	public static final String CLASSROOM_CHAT = "/ws/oj/classroom/chat";

	public static final String CLASSROOM_HELP = "/ws/oj/classroom/help";

	public static final String OPERATION_SCENE = "/ws/oj/operation/scene";

	public static final String LESSON_SCENE = "/ws/course/lesson_scene";

	public static final String LESSON_PRACTICE_SCENE = "/ws/course/lesson/operation/scene";

	public static final String CLASSROOM_SCENE = "/ws/oj/classroom/scene";

	public static final String CLASSROOM_GROUP_SCENE = "/ws/oj/classroom/group/scene";

	public static final String ROOM_SCENE_FIFTEEN_MIN_TIPS = "/ws/oj/classroom/scene/tips";

	public static final String SITUATION = "/ws/situation/scene";

	public static final String NOTICE = "/ws/cr/ce_base/notice";

	private static Set<String> cache = Sets.newHashSet();

	static {
		cache.add(CR_SCENE);
		cache.add(CR_MISSION);
		cache.add(CR_CHAT);
		cache.add(OPERATION_SCENE);
		cache.add(LESSON_SCENE);
		cache.add(LESSON_PRACTICE_SCENE);
		cache.add(CLASSROOM_SCENE);
		cache.add(CLASSROOM_GROUP_SCENE);
		cache.add(SITUATION);
		cache.add(CLASSROOM_CHAT);
		cache.add(CLASSROOM_HELP);
		cache.add(ROOM_SCENE_FIFTEEN_MIN_TIPS);
		cache.add(NOTICE);
	}

	public static boolean validPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return cache.contains(path);
	}
}
