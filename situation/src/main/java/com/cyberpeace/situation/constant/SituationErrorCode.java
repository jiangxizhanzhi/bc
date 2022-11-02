package com.cyberpeace.situation.constant;

public interface SituationErrorCode {

	/** 动效方案名称重复 */
	String DUPLICATED_DYNAMIC_SCHEME_NAME = "104001";

	/** 阶段内动效描述重复 */
	String DUPLICATED_DYNAMIC_DESCRIPTION = "104002";

	/** 阶段名称不能为空 */
	String EMPTY_STAGE_NAME = "104003";

	/** 动效方案名称不能为空 */
	String EMPTY_SCHEME_NAME = "104004";

	/** 源节点为空 */
	String EMPTY_SOURCE_NODE = "104005";

	/** 导出动效方案失败 */
	String FAILED_TO_EXPORT_DYNAMIC_SCHEME = "104006";

	/** 动效方案中的部分节点在该场景中不存在 */
	String SOME_NODES_NOT_EXIST = "104007";

	/** 动效方案名称不合法 */
	String INVALID_DYNAMIC_SCHEME_NAME = "104008";

	/** 阶段名称不合法 */
	String INVALID_STAGE_NAME = "104009";

	/** 动效描述不合法 */
	String INVALID_DYNAMIC_DESCRIPTION = "104010";

	/** 未找到有效场景配置 */
	String NO_VALID_SCENE_CONFIG = "104011";

	/** 本场景未配置流量采集 */
	String FLOW_COLLECTION_IS_NOT_CONFIGURED = "104012";

	/** 本场景未配置仿真流量 */
	String FLOW_SIMULATION_IS_NOT_CONFIGURED = "104013";

	/** 多个多媒体动效存在时间重叠 */
	String MULTIMEDIA_MEDIA_ANIMATION_HAVE_TIME_OVERLAP = "104014";

	/** 非场景态势暂时无法使用控制台 */
	String UNAVAILABLE_FOR_NON_SCENE_SITUATION = "104015";

	/** 当前场景节点无坐标 */
	String NODE_HAS_NO_COORDINATES = "104016";

	/** 二维组件名称不能为空 */
	String NAME_EMPTY_ERROR = "104017";

	/** 图表类型不能为空 */
	String TYPE_EMPTY_ERROR = "104018";

	/** 缩略图不能为空 */
	String THUMBNAIL_EMPTY_ERROR = "104019";

	/** 组件代码不能为空 */
	String COMPONENT_CODE_EMPTY_ERROR = "104020";

	/** 资源id不能为空 */
	String RESOURCE_ID_EMPTY_ERROR = "104021";

	/** 图表标准文件不能为空 */
	String CHART_STANDARD_DOC_EMPTY_ERROR = "104022";

	/** 类型参数无效 */
	String TYPE_INVALID_ERROR = "104023";

	/** 源节点动效文件不能为空 */
	String SOURCE_NODE_DYNAMIC_FILE_EMPTY_ERROR = "104024";

	/** 目标节点动效文件不能为空 */
	String TARGET_NODE_DYNAMIC_FILE_EMPTY_ERROR = "104025";

	/** 聚焦不能为空 */
	String FOCUS_EMPTY_ERROR = "104026";

	/** 颜色不能为空 */
	String COLOR_EMPTY_ERROR = "104027";

	/** 线形不能为空 */
	String LINE_EMPTY_ERROR = "104028";

	/** 线形大小不能为空 */
	String LINE_SIZE_DOC_EMPTY_ERROR = "104029";
}
