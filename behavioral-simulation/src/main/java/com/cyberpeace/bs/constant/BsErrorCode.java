package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟自定义错误码
 * @author SN
 */
public final class BsErrorCode {

	/**
	 * 仪表已开始
	 */
	public static final String ALREADY_STARTED = "105001";

	/**
	 * 无效的访问配置
	 */
	public static final String INVALID_ACCESS_CONF = "105002";

	/**
	 * 无效的行为配置
	 */
	public static final String INVALID_BEHAVIOR_CONF = "105003";

	/**
	 * 行为开始时间未配置
	 */
	public static final String START_TIME_NOT_CONFIGURED = "105004";

	/**
	 * id列表为空
	 */
	public static final String EMPTY_ID_COLL = "105005";

	/**
	 * 请注明是否实体设备
	 */
	public static final String ACTUAL_OR_VIRTUAL = "105006";

	/**
	 * resource_id 不存在
	 */
	public static final String RESOURCE_ID_MISSING = "105007";

	/**
	 * 设备id为空
	 */
	public static final String INVALID_DEVICE_ID = "105008";

	/**
	 * 设备ip为空
	 */
	public static final String INVALID_DEVICE_IP = "105009";

	/**
	 * 无效的设备类型
	 */
	public static final String INVALID_DEVICE_TYPE = "105010";

	/**
	 * 无效的端口配置
	 */
	public static final String INVALID_PORT = "105011";

	/**
	 * 未配置龙测API服务Token
	 */
	public static final String TOKEN_NOT_CONFIGURED = "105012";

	/**
	 * 指定用户模型不存在
	 */
	public static final String USER_MODEL_MISSING = "105013";

	/**
	 * 未配置外部网络网段
	 */
	public static final String SEGMENT_NOT_CONFIGURED = "105014";

	/**
	 * 导出行为失败
	 */
	public static final String FAILED_TO_EXPORT = "105015";

	/**
	 * 读取上传的文件失败
	 */
	public static final String FAILED_TO_LOAD_FILE = "105016";

	/**
	 * 场景节点id为空
	 */
	public static final String NODE_ID_MISSING = "105017";

	/**
	 * 行为仪表不存在
	 */
	public static final String SIMULATOR_NOT_FOUND = "105018";

	/**
	 * 错误的时间范围；开始时间不能在结束时间之后
	 */
	public static final String TIME_RANGE_ERROR = "105019";

	/**
	 * 设备没有找到
	 */
	public static final String DEVICE_NOT_FOUND = "105020";

	/**
	 * 空设备id
	 */
	public static final String EMPTY_DEVICE_ID = "105021";
	/**
	 * 没有在线设备
	 */
	public static final String NO_ONLINE_DEVICE = "105022";
	/**
	 * 多个在线设备
	 */
	public static final String MULTIPLE_DEVICE = "105023";
	/**
	 * 部分行为的录制设备与行为集合不匹配
	 */
	public static final String ILLEGAL_BEHAVIOR_OS = "105024";
	/**
	 * 设备离线
	 */
	public static final String DEVICE_OFFLINE = "105025";

	/**
	 * 部分设备离线
	 */
	public static final String SOME_DEVICE_OFFLINE = "105026";

	/**
	 * 部分行为的录制设备与行为模型不匹配
	 */
	public static final String ILLEGAL_BEHAVIOR_OS_UM = "105027";
}
