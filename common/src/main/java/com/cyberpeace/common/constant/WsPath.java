/**
 * 这段代码定义了一个常量类 `WsPath`，用于存储 WebSocket 路径相关的常量，并提供路径验证的功能。以下是代码的主要分析： 
 * ### 主要功能 
 * 1. **常量定义**： 
 * - 定义了一系列静态常量，表示不同的 WebSocket 路径，例如 `/ws/cr/ce_base/scene`、`/ws/oj/classroom/chat` 等。 
 * - 每个常量都有注释说明其用途。 
 * 2. **缓存机制**： 
 * - 使用 `Guava` 的 `Sets.newHashSet()` 创建了一个 `Set` 类型的静态变量 `cache`，存储所有已定义的路径。 
 * - 在静态块中将所有路径添加到 `cache` 中，以便后续快速查找。 
 * 3. **路径验证方法**： 
 * - 提供了 `validPath(String path)` 方法，用于检查传入的路径是否在 `cache` 中存在。 
 * - 如果路径为空或为无效值（通过 `StringUtils.isBlank()` 判断），直接返回 `false`；否则检查路径是否存在于 `cache` 中。 
 * ### 优点 
 * - **模块化设计**：所有路径集中在一个类中管理，便于维护和扩展。 
 * - **高效查找**：使用 `Set` 数据结构，确保路径查找的时间复杂度为 O(1)。 
 * - **健壮性**：通过 `StringUtils.isBlank()` 过滤无效路径，避免空值问题。 
 * ### 可能的改进建议 
 * 1. **路径规范化**： 
 * - 可以在 `validPath` 方法中对路径进行标准化处理（如去除首尾空格或统一大小写），以提高匹配的灵活性。 
 * 2. **线程安全性**： 
 * - 当前 `cache` 是一个普通的 `Set`，如果多线程环境中可能需要考虑线程安全问题（如使用 `Collections.synchronizedSet` 或 `ConcurrentHashMap`）。 
 * 3. **动态更新**： 
 * - 如果路径是动态变化的，可以考虑使用其他数据结构（如数据库或配置文件）来存储路径，而不是硬编码。 
 * 总体来说，这段代码逻辑清晰，结构合理，适合用作 WebSocket 路径管理的基础工具类。 
**/
package com.cyberpeace.common.constant;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 对外暴露的websocket路径
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public class WsPath {

	/**
	 * 单兵、团队、红蓝的场景启动、节点状态变更、态势消息，使用此path
	 *
	 * @see String
	 */
	public static final String CR_SCENE = "/ws/cr/ce_base/scene";

	/**
	 * 靶场任务
	 *
	 * @see String
	 */
	public static final String CR_MISSION = "/ws/cr/ce_base/mission";

	/**
	 * 靶场聊天
	 *
	 * @see String
	 */
	public static final String CR_CHAT = "/ws/cr/ce_base/chat";

	/**
	 * 课堂聊天
	 *
	 * @see String
	 */
	public static final String CLASSROOM_CHAT = "/ws/oj/classroom/chat";

	/**
	 * 课堂帮助
	 *
	 * @see String
	 */
	public static final String CLASSROOM_HELP = "/ws/oj/classroom/help";

	/**
	 * 实操场景
	 *
	 * @see String
	 */
	public static final String OPERATION_SCENE = "/ws/oj/operation/scene";

	/**
	 * 课场景
	 *
	 * @see String
	 */
	public static final String LESSON_SCENE = "/ws/course/lesson_scene";

	/**
	 * 课实践场景
	 *
	 * @see String
	 */
	public static final String LESSON_PRACTICE_SCENE = "/ws/course/lesson/operation/scene";

	/**
	 * 课堂场景
	 *
	 * @see String
	 */
	public static final String CLASSROOM_SCENE = "/ws/oj/classroom/scene";

	/**
	 * 课堂群场景
	 *
	 * @see String
	 */
	public static final String CLASSROOM_GROUP_SCENE = "/ws/oj/classroom/group/scene";

	/**
	 * 房间场景十五分钟技巧
	 *
	 * @see String
	 */
	public static final String ROOM_SCENE_FIFTEEN_MIN_TIPS = "/ws/oj/classroom/scene/tips";

	/**
	 * 态势
	 *
	 * @see String
	 */
	public static final String SITUATION = "/ws/situation/scene";

	/**
	 * 通知
	 *
	 * @see String
	 */
	public static final String NOTICE = "/ws/cr/ce_base/notice";

	/**
	 * 练习纸
	 *
	 * @see String
	 */
	public static final String EXERCISE_PAPER = "/ws/cr/ce_base/exercise_paper";

	/**
	 * 缓存
	 *
	 * @see Set<String>
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

	public static boolean validPath(String path) {
		if (StringUtils.isBlank(path)) {
			return false;
		}
		return cache.contains(path);
	}
}
