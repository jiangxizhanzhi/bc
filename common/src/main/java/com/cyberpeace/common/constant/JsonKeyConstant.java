package com.cyberpeace.common.constant;

/**
 * Json key定义，复用切易查找
 */
public final class JsonKeyConstant {

	/** 倒计时*/
	public static final String REMAIN_TIME = "remaining_time";

	/** 课堂分组编号*/
	public static final String GROUP_ID = "group_id";

	/** 场景状态*/
	public static final String STATUS = "status";

	/** 场景类型 1=私有 2=共享 3=小组*/
	public static final String SCENE_SHARE_MODE = "scene_share_mode";

	/**  场景类型 对应LocalScene.SceneStatusEnum枚举值*/
	public static final String REFERENCE_TYPE = "reference_type";

	public static final String ENABLE_RECORDING = "enable_recording";

	/** 返回数据体*/
	public static final String DATA = "data";

	/** 场景信息key值*/
	public static final String SCENE_DATA = "scene";

	/** 场景事件key值 对应SceneEvent枚举值*/
	public static final String SCENE_EVENT = "event";

	/** 场景事件状态 对应SceneEventStatus枚举值*/
	public static final String SCENE_EVENT_STATUS = "event_status";

	/** 推送消息使用*/
	public static final String CODE = "code";

	/** 场景数据配置key*/
	public static final String SCENE_CONFIG = "scene_config";

	/** 拓扑数据key*/
	public static final String TOPOLOGY = "topology";

	/** 节点数据key*/
	public static final String ELEMENTS = "elements";

	/** 场景数据中初始化命令key 用于获取动态设置*/
	public static final String INIT_COMMANDS = "init_commands";

	/** 获取动态flag注入命令*/
	public static final String NODE_COMMAND = "node_command";

	/** 会话信息用户编号key*/
	public static final String USER_KEY = "user";

	/** 远程协助地址字短*/
	public static final String ASSISTANCE_URL = "assistance_url";

	/** 节点名称key*/
	public static final String NODE_NAME = "node_name";

	/** 习题类型*/
	public static final String PRACTICE_KEY = "practice";

	/** 录屏*/
	public static final String SCREEN_RECORDING = "screen_recording";

	/** 业务编号*/
	public static final String BUSINESS_ID = "business_id";

	/**  业务类型*/
	public static final String BUSINESS_TYPE = "business_type";

	public static final String KEY = "key";

	public static final String NAME = "name";

	public static final String ID = "id";

	public static final String CHILDREN = "children";

	public static final String PATH = "path";

	public static final String RESOURCE_ID = "resource_id";

	public static final String CREATE_USER_ID = "create_user_id";

	public static final String START_TIME = "start_time";

	public static final String END_TIME = "end_time";

	public static final String CLASSIFY = "classify";

	public static final String TYPE = "type";

	public static final String ROOM_ID = "room_id";

	public static final String PARENT = "parent";

	public static final String SCENE_NAME = "scene_name";

	public static final String IMAGE_IDS = "image_ids";

	public static final String MESSAGE = "message";

	public static final String CP_CLOUD = "cpcloud";

	public static final String PROTOCOL = "protocol";

	public static final String PORT_MAPPING = "port_mapping";

	public static final String PORT = "port";

	public static final String ACCESS_IP = "access_ip";

	public static final String ACCESS_PORT = "access_port";

	private JsonKeyConstant() {}
}
