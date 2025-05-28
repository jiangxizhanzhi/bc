/**
 * 这段代码定义了一个名为 `JsonKeyConstant` 的接口，主要用于存储 JSON 数据中的键值常量。以下是对其的简要分析： 
 * 1. **功能**： 
 * - 该接口提供了一系列字符串常量，这些常量代表了 JSON 数据中常用的键名。 
 * - 它旨在提高代码的可读性、复用性和维护性，避免硬编码。 
 * 2. **结构**： 
 * - 使用 Java 接口（`interface`）来定义常量。 
 * - 每个常量都有一个注释，描述其含义或用途。 
 * 3. **主要用途**： 
 * - 适用于 JSON 数据解析和生成场景，尤其是在前后端交互或数据传输中频繁使用的键值。 
 * - 提供统一的命名规范，便于团队协作和代码维护。 
 * 4. **示例场景**： 
 * - 在处理 JSON 数据时，可以直接引用这些常量，例如： 
 * ```java 
 * String key = JsonKeyConstant.ID; 
 * ``` 
 * 5. **优点**： 
 * - 避免重复定义相同的键值。 
 * - 提高代码的一致性和可维护性。 
 * - 易于查找和修改键值。 
 * 总结：这是一个典型的常量接口，专门用于管理 JSON 键值，适合在需要频繁使用 JSON 数据的项目中应用。 
**/
package com.cyberpeace.common.constant;

/**
 * Json key定义，复用切易查找
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface JsonKeyConstant {

	/**
	 * 倒计时
	 *
	 * @see String
	 */
	String REMAIN_TIME = "remaining_time";

	/**
	 * 课堂分组编号
	 *
	 * @see String
	 */
	String GROUP_ID = "group_id";

	/**
	 * 场景状态
	 *
	 * @see String
	 */
	String STATUS = "status";

	/**
	 * 场景类型 1=私有 2=共享 3=小组
	 *
	 * @see String
	 */
	String SCENE_SHARE_MODE = "scene_share_mode";

	/**
	 * 场景类型 对应LocalScene.SceneStatusEnum枚举值
	 *
	 * @see String
	 */
	String REFERENCE_TYPE = "reference_type";

	/**
	 * 启用录制
	 *
	 * @see String
	 */
	String ENABLE_RECORDING = "enable_recording";

	/**
	 * 返回数据体
	 *
	 * @see String
	 */
	String DATA = "data";

	/**
	 * 场景信息key值
	 *
	 * @see String
	 */
	String SCENE_DATA = "scene";

	/**
	 * 场景事件key值 对应SceneEvent枚举值
	 *
	 * @see String
	 */
	String SCENE_EVENT = "event";

	/**
	 * 场景事件状态 对应SceneEventStatus枚举值
	 *
	 * @see String
	 */
	String SCENE_EVENT_STATUS = "event_status";

	/**
	 * 推送消息使用
	 *
	 * @see String
	 */
	String CODE = "code";

	/**
	 * 场景数据配置key
	 *
	 * @see String
	 */
	String SCENE_CONFIG = "scene_config";

	/**
	 * 拓扑数据key
	 *
	 * @see String
	 */
	String TOPOLOGY = "topology";

	/**
	 * 节点数据key
	 *
	 * @see String
	 */
	String ELEMENTS = "elements";

	/**
	 * 场景数据中初始化命令key 用于获取动态设置
	 *
	 * @see String
	 */
	String INIT_COMMANDS = "init_commands";

	/**
	 * 获取动态flag注入命令
	 *
	 * @see String
	 */
	String NODE_COMMAND = "node_command";

	/**
	 * 会话信息用户编号key
	 *
	 * @see String
	 */
	String USER_KEY = "user";

	/**
	 * 远程协助地址字短
	 *
	 * @see String
	 */
	String ASSISTANCE_URL = "assistance_url";

	/**
	 * 节点名称key
	 *
	 * @see String
	 */
	String NODE_NAME = "node_name";

	/**
	 * 习题类型
	 *
	 * @see String
	 */
	String PRACTICE_KEY = "practice";

	/**
	 * 录屏
	 *
	 * @see String
	 */
	String SCREEN_RECORDING = "screen_recording";

	/**
	 * 业务编号
	 *
	 * @see String
	 */
	String BUSINESS_ID = "business_id";

	/**
	 * 业务类型
	 *
	 * @see String
	 */
	String BUSINESS_TYPE = "business_type";

	/**
	 * 钥匙
	 *
	 * @see String
	 */
	String KEY = "key";

	/**
	 * 名称
	 *
	 * @see String
	 */
	String NAME = "name";

	/**
	 * id
	 *
	 * @see String
	 */
	String ID = "id";

	/**
	 * 子级
	 *
	 * @see String
	 */
	String CHILDREN = "children";

	/**
	 * 路径
	 *
	 * @see String
	 */
	String PATH = "path";

	/**
	 * 资源id
	 *
	 * @see String
	 */
	String RESOURCE_ID = "resource_id";

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	String CREATE_USER_ID = "create_user_id";

	/**
	 * 开始时间
	 *
	 * @see String
	 */
	String START_TIME = "start_time";

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	String END_TIME = "end_time";

	/**
	 * 分类
	 *
	 * @see String
	 */
	String CLASSIFY = "classify";

	/**
	 * 类型
	 *
	 * @see String
	 */
	String TYPE = "type";

	/**
	 * 房间id
	 *
	 * @see String
	 */
	String ROOM_ID = "room_id";

	/**
	 * 父母
	 *
	 * @see String
	 */
	String PARENT = "parent";

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	String SCENE_NAME = "scene_name";

	/**
	 * 图像id列表
	 *
	 * @see String
	 */
	String IMAGE_IDS = "image_ids";

	/**
	 * 消息
	 *
	 * @see String
	 */
	String MESSAGE = "message";
}
