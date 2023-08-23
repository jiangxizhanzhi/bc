package com.cyberpeace.common.constant;

/**
 * Json key定义，复用切易查找
 */
public interface JsonKeyConstant {

	/** 倒计时*/
	String REMAIN_TIME = "remaining_time";

	/** 课堂分组编号*/
	String GROUP_ID = "group_id";

	/** 场景状态*/
	String STATUS = "status";

	/** 场景类型 1=私有 2=共享 3=小组*/
	String SCENE_SHARE_MODE = "scene_share_mode";

	/**  场景类型 对应LocalScene.SceneStatusEnum枚举值*/
	String REFERENCE_TYPE = "reference_type";

	String ENABLE_RECORDING = "enable_recording";

	/** 返回数据体*/
	String DATA = "data";

	/** 场景信息key值*/
	String SCENE_DATA = "scene";

	/** 场景事件key值 对应SceneEvent枚举值*/
	String SCENE_EVENT = "event";

	/** 场景事件状态 对应SceneEventStatus枚举值*/
	String SCENE_EVENT_STATUS = "event_status";

	/** 推送消息使用*/
	String CODE = "code";

	/** 场景数据配置key*/
	String SCENE_CONFIG = "scene_config";

	/** 拓扑数据key*/
	String TOPOLOGY = "topology";

	/** 节点数据key*/
	String ELEMENTS = "elements";

	/** 场景数据中初始化命令key 用于获取动态设置*/
	String INIT_COMMANDS = "init_commands";

	/** 获取动态flag注入命令*/
	String NODE_COMMAND = "node_command";

	/** 会话信息用户编号key*/
	String USER_KEY = "user";

	/** 远程协助地址字短*/
	String ASSISTANCE_URL = "assistance_url";

	/** 节点名称key*/
	String NODE_NAME = "node_name";

	/** 习题类型*/
	String PRACTICE_KEY = "practice";

	/** 录屏*/
	String SCREEN_RECORDING = "screen_recording";

	/** 业务编号*/
	String BUSINESS_ID = "business_id";

	/**  业务类型*/
	String BUSINESS_TYPE = "business_type";

	String KEY = "key";

	String NAME = "name";

	String ID = "id";

	String CHILDREN = "children";

	String PATH = "path";

	String RESOURCE_ID = "resource_id";

	String CREATE_USER_ID = "create_user_id";

	String START_TIME = "start_time";

	String END_TIME = "end_time";

	String CLASSIFY = "classify";

	String TYPE = "type";

	String ROOM_ID = "room_id";

	String PARENT = "parent";

	String SCENE_NAME = "scene_name";

	String IMAGE_IDS = "image_ids";

	String MESSAGE = "message";

	String CREATE_TIME = "create_time";

	String DESC = "desc";

}
