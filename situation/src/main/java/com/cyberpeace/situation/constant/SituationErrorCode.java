package com.cyberpeace.situation.constant;

/**
 * 态势错误代码
 *
 * @author chenhy
 * @date 2023/05/06
 */
public interface SituationErrorCode {

	/**
	 *  动效方案名称重复
	 */
	String DUPLICATED_DYNAMIC_SCHEME_NAME = "104001";

	/** 
	 * 阶段内动效描述重复 
	 */
	String DUPLICATED_DYNAMIC_DESCRIPTION = "104002";

	/** 
	 * 阶段名称不能为空
	 */
	String EMPTY_STAGE_NAME = "104003";

	/** 
	 * 动效方案名称不能为空 
	 */
	String EMPTY_SCHEME_NAME = "104004";

	/** 
	 * 源节点为空 
	 */
	String EMPTY_SOURCE_NODE = "104005";

	/** 
	 * 导出动效方案失败 
	 */
	String FAILED_TO_EXPORT_DYNAMIC_SCHEME = "104006";

	/** 
	 * 动效方案中的部分节点在该场景中不存在 
	 */
	String SOME_NODES_NOT_EXIST = "104007";

	/** 
	 * 动效方案名称不合法 
	 */
	String INVALID_DYNAMIC_SCHEME_NAME = "104008";

	/** 
	 * 阶段名称不合法 
	 */
	String INVALID_STAGE_NAME = "104009";

	/** 
	 * 动效描述不合法 
	 */
	String INVALID_DYNAMIC_DESCRIPTION = "104010";

	/** 
	 * 未找到有效场景配置 
	 */
	String NO_VALID_SCENE_CONFIG = "104011";

	/** 
	 * 本场景未配置流量采集 
	 */
	String FLOW_COLLECTION_IS_NOT_CONFIGURED = "104012";

	/** 
	 * 本场景未配置仿真流量 
	 */
	String FLOW_SIMULATION_IS_NOT_CONFIGURED = "104013";

	/** 
	 * 多个多媒体动效存在时间重叠 
	 */
	String MULTIMEDIA_MEDIA_ANIMATION_HAVE_TIME_OVERLAP = "104014";

	/** 
	 * 非场景态势暂时无法使用控制台 
	 */
	String UNAVAILABLE_FOR_NON_SCENE_SITUATION = "104015";

	/** 
	 * 当前场景节点无坐标 
	 */
	String NODE_HAS_NO_COORDINATES = "104016";

	/** 
	 * 二维组件名称不能为空 
	 */
	String NAME_EMPTY_ERROR = "104017";

	/** 
	 * 图表类型不能为空 
	 */
	String TYPE_EMPTY_ERROR = "104018";

	/** 
	 * 缩略图不能为空 
	 */
	String THUMBNAIL_EMPTY_ERROR = "104019";

	/** 
	 * 组件代码不能为空 
	 */
	String COMPONENT_CODE_EMPTY_ERROR = "104020";

	/** 
	 * 资源id不能为空 
	 */
	String RESOURCE_ID_EMPTY_ERROR = "104021";

	/** 
	 * 图表标准文件不能为空 
	 */
	String CHART_STANDARD_DOC_EMPTY_ERROR = "104022";

	/**
	 * 类型参数无效 
	 */
	String TYPE_INVALID_ERROR = "104023";

	/** 
	 * 源节点动效文件不能为空 
	 */
	String NODE_DYNAMIC_FILE_EMPTY_ERROR = "104024";

	/** 
	 * 目标节点动效文件不能为空 
	 */
	String TARGET_NODE_DYNAMIC_FILE_EMPTY_ERROR = "104025";

	/** 
	 * 聚焦不能为空 
	 */
	String FOCUS_EMPTY_ERROR = "104026";

	/** 
	 * 颜色不能为空 
	 */
	String COLOR_EMPTY_ERROR = "104027";

	/** 
	 * 线形不能为空 
	 */
	String LINE_EMPTY_ERROR = "104028";

	/** 
	 * 线形大小不能为空 
	 */
	String LINE_SIZE_DOC_EMPTY_ERROR = "104029";

	/** 
	 * 资源文件不能为空 
	 */
	String RESOURCE_FILE_EMPTY_ERROR = "104030";

	/** 
	 * 该类型下包含组件不能删除 
	 */
	String TYPE_WITH_COMPONENTS_ERROR = "104031";

	/** 
	 * 想定方案不能为空 
	 */
	String PLAN_ID_EMPTY_ERROR = "104032";

	/** 
	 * 任务不能为空 
	 */
	String TASK_EMPTY_ERROR = "104033";

	/** 
	 * 科目不能为空 
	 */
	String SUBJECT_EMPTY_ERROR = "104034";

	/** 
	 * 模板不能为空 
	 */
	String TEMPLATE_EMPTY_ERROR = "104035";

	/** 
	 * 态势类型不能为空 
	 */
	String SITUATION_TYPE_EMPTY_ERROR = "104036";

	/** 
	 * 态势类型id错误 
	 */
	String SITUATION_TYPE_INVALID_ERROR = "104037";

	/** 
	 * 当前已有发布大屏 
	 */
	String RELEASE_REPEAT_ERROR = "104038";

	/** 
	 * 包含发布大屏不可删除 
	 */
	String RELEASE_SITUATION_INSTANCE_CANNOT_DELETE_ERROR = "104039";

	/** 
	 * status无效 
	 */
	String STATUS_INVALID_ERROR = "104040";

	/** 
	 * 模板id无效
	 */
	String TEMPLATE_INVALID_ERROR = "104041";

	/** 
	 * 任务未开始 
	 */
	String TASK_NOT_RUNNING = "106001";

	/** 
	 * 暂不支持的任务大屏 
	 */
	String TASK_TYPE_NOT_SUPPORTED = "106002";

	/**
	 * 远程发布大屏失败
	 */
	String REMOTE_FAILED_TO_RELEASE = "106003";

	/**
	 * 未知的事件类型
	 */
	String UNKNOWN_EVENT_TYPE = "106004";

	/**
	 * 不允许修改任务方案
	 */
	String CAN_NOT_MODIFY_TASK = "106005";

	/**
	 * 暂无已发布的大屏
	 */
	String NO_RELEASED_DASHBOARD = "106006";

	/**
	 * 图层数量异常
	 */
	String LAYER_NUM_ERROR = "106007";

	/**
	 * 想定大屏不允许自行取消
	 */
	String XD_DASHBOARD_CANCEL_ERROR = "106008";

	/**
	 * 态势模板未关联拓扑
	 */
	String UNRELATED_TO_TOPO = "106009";

	/**
	 * 可视化方案不允许为空
	 */
	String SCHEME_EMPTY_ERROR = "106010";

	/**
	 * 未找到默认靶场大屏信息
	 */
	String DEFAULT_BC_ERROR = "106011";

	/**
	 * 二维组件导出失败
	 */
	String COMPONENTS_EXPORT_FAILED = "106012";

	/**
	 * 二维组件导入失败
	 */
	String COMPONENTS_IMPORT_FAILED = "106013";

	/**
	 * 获取大屏信息失败
	 */
	String FAILED_TO_OBTAIN_DASHBOARD_INFO = "106014";

	/**
	 * 靶场下已存在该类型事件的动效映射
	 */
	String DYNAMIC_EFFECT_MAPPING_EXIST = "106015";

	/**
	 * 科目信息异常
	 */
	String SUBJECT_INFO_ERROR = "106016";

	/**
	 * 导入格式不正确
	 */
	String IMPORT_FORMAT_FAILED = "106017";

	/**
	 * 模板数据异常
	 */
	String TEMPLATE_DATA_ERROR = "106018";

	/**
	 * 添加动效失败
	 */
	String ADD_DYNAMIC_EFFECT_FAILED = "106019";

	/**
	 * 部分动效正在使用
	 */
	String DYNAMIC_EFFECT_IN_USE = "106020";

	/**
	 * 预设映射不允许修改
	 */
	String PRESET_MAPPING_CAN_NOT_UPDATE = "106021";

	/**
	 * 预设映射不允许删除
	 */
	String PRESET_MAPPING_CAN_NOT_DELETE = "106022";

	/**
	 * 动效文件不存在
	 */
	String DYNAMIC_EFFECT_FILE_NOT_FOUND = "106023";

	/**
	 * 图表主题名称已存在
	 */
	String COLOR_THEME_NAME_EXIST = "106024";

	/**
	 * 动效分组正在使用
	 */
	String DYNAMIC_EFFECTS_CATEGORY_USED = "106025";

	String CANT_FIND_SCHEME = "106026";

	String CHOOSE_TEMPLATE = "106027";

	String BLANK_NAME = "106028";

	String NOT_KNOWN_COMMAND = "106029";
}
