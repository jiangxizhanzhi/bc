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

	/** 找不到对应动效 */
	String CANNOT_FIND_DYNAMIC = "104017";

	/** 导调方案名称不合法 */
	String INVALID_GUIDE_SCHEME_NAME = "104018";

	/** 导调方案名称重复 */
	String DUPLICATED_GUIDE_SCHEME_NAME = "104019";

	/** 导调方案参数不合法 */
	String INVALID_GUIDE_SCHEME = "104020";

}
