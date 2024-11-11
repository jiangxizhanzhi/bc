package com.cyberpeace.common.constant;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

/**
 * 对外暴露的websocket路径
 *
 * @author SN
 * @date 2024/11/07
 */
public class WsPath {

	/**
	 * 单兵、团队、红蓝的场景启动、节点状态变更、态势消息，使用此path
	 */
	public static final String CR_SCENE = "/ws/cr/ce_base/scene";

	/**
	 * 靶场任务
	 */
	public static final String CR_MISSION = "/ws/cr/ce_base/mission";

	/**
	 * 靶场聊天
	 */
	public static final String CR_CHAT = "/ws/cr/ce_base/chat";

	/**
	 * 课堂聊天
	 */
	public static final String CLASSROOM_CHAT = "/ws/oj/classroom/chat";

	/**
	 * 课堂帮助
	 */
	public static final String CLASSROOM_HELP = "/ws/oj/classroom/help";

	/**
	 * 实操场景
	 */
	public static final String OPERATION_SCENE = "/ws/oj/operation/scene";

	/**
	 * 课场景
	 */
	public static final String LESSON_SCENE = "/ws/course/lesson_scene";

	/**
	 * 课练习场景
	 */
	public static final String LESSON_PRACTICE_SCENE = "/ws/course/lesson/operation/scene";

	/**
	 * 课堂场景
	 */
	public static final String CLASSROOM_SCENE = "/ws/oj/classroom/scene";

	/**
	 * 课堂群场景
	 */
	public static final String CLASSROOM_GROUP_SCENE = "/ws/oj/classroom/group/scene";

	/**
	 * 房间场景十五分钟技巧
	 */
	public static final String ROOM_SCENE_FIFTEEN_MIN_TIPS = "/ws/oj/classroom/scene/tips";

	/**
	 * 情况
	 */
	public static final String SITUATION = "/ws/situation/scene";

	/**
	 * 通知
	 */
	public static final String NOTICE = "/ws/cr/ce_base/notice";

	/**
	 * 练习试卷
	 */
	public static final String EXERCISE_PAPER = "/ws/cr/ce_base/exercise_paper";

	/**
	 * 缓存
	 */
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
		cache.add(EXERCISE_PAPER);
	}

	/**
	 * 有效路径
	 *
	 * @param path 路径
	 * @return boolean
	 */
	public static boolean validPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return cache.contains(path);
	}
}
