package com.cyberpeace.common.constant;

/**
 * 统一错误吗
 */
public interface ErrorCode {

	/**
	 * 参数校验异常
	 */
	String METHOD_ARG_NOT_VALID = "000001";

	/**
	 * 数据库主键重复
	 */
	String DUPLICATE_KEY = "000002";

	/**
	 * 通过参数中的resource_id无法找到对应资源
	 * */
	String RESOURCE_NOT_FOUND = "000003";

	String REMOTE_SERVICE_EXCEPTION = "010000";

	String INTERNAL_ERROR = "099999";

	// 文件上传失败
	String FILE_UPLOAD_FAIL = "100011";

	/** 文件拷贝失败*/
	String FILE_COPY_FAIL = "100012";

	/** 文件压缩失败*/
	String FILE_ZIP_FAIL = "100013";

	/** 文件类型不正确*/
	String FILE_TYPE_ERROR = "100014";

	/** 文件读取失败*/
	String FILE_READ_FAIL = "100015";

	/** 用户不在列表中 */
	String USER_NOT_IN_LIST = "100016";

	/** 对应文件不存在*/
	String FILE_NOT_EXIST = "100017";

	/** 子类未实现 */
	String SUB_NOT_IMPLEMENT = "100018";

	/** 类型错误 */
	String TYPE_ERROR = "100019";

	/** 场景启动失败*/
	String SCENE_RUNNING_FAILED = "100020";

	/** 无效文件错误 */
	String FILE_CONTENT_INVALID = "100021";

	/** 复制文件失败*/
	String FAILED_TO_COPY_FILE = "100022";

	/** 场景未暂停*/
	String NOT_PAUSED = "100023";

	/** 保存文件失败*/
	String FAILED_TO_SAVE_FILE = "100024";

	/** 获取Office文件信息失败*/
	String FAILED_TO_OBTAIN_OFFICE_FILE_INFO = "100025";

	/** 未配置 */
	String NOT_CONFIGURED = "100026";

	/** 无效的配置 */
	String INVALID_CONFIGURATION = "100027";

	/**
	 * 操作过于频繁
	 */
	String OPERATION_TOO_FREQUENT = "100028";

	/**
	 * 用户信息缺失/用户不存在
	 */
	String USER_INFO_MISSING = "100029";

	/**
	 * 场景节点不足
	 */
	String LACK_OF_SCENE_NODE = "100030";

	/** 无效的文件路径 */
	String INVALID_FILE_PATH = "100031";

	/** 文件为空 */
	String EMPTY_FILE = "100032";

	/** 创建文件夹失败 */
	String FAILED_TO_MAKE_DIR = "100033";

	/** 重启节点失败 */
	String FAILED_TO_RESTART_NODE = "100034";

	/** 重建节点失败 */
	String FAILED_TO_RECREATE_NODE = "100035";

	/** 写入文件失败 */
	String FAILED_TO_WRITE_FILE = "100036";

	/** 名称已存在 */
	String NAME_DUPLICATE = "100037";

	/** 场景关闭失败 */
	String FAILED_TO_STOP_SCENE = "100038";

	/** 不支持的文件类型，仅支持xxx*/
	String NOT_SUPPORT_FILE_TYPE = "100039";

	/**
	 * 靶场测评算法脚本无返回值
	 */
	String ALGORITHM_RETURN_NULL = "100040";

	/** shell执行失败*/
	String LOCAL_SHELL_EXEC_FAILED = "100041";

	/** 场景未启动*/
	String SCENE_NOT_RUNNING = "100042";

	/** 新建快照失败*/
	String NODE_SNAPSHOT_FAILED = "100043";

	/** 文件大小超限制*/
	String FILE_SIZE_EXCEED_UPPER_LIMIT = "100044";

	/** 名称已存在*/
	String REPEAT_PRACTICE_SET_NANE = "100045";

	/** 找不到文件: [%s]*/
	String FILE_ERROR_FAIL = "100046";

	/** 无操作权限*/
	String INTERFACE_UNAUTHORIZED = "100047";

	String SCENE_NOT_EXIST_ZH = "场景不存在";

	String SCENE_NOT_EXIST_EN = "Scene not exist";

	/** 文件删除失败*/
	String FILE_DELETE_FAILED = "100048";

	/** 竞赛服务异常*/
	String ERROR_AD_SERVICE = "100049";

	/** 创建启动节点失败 */
	String FAILED_TO_CREATE_NODE = "100050";

	/** 图片后缀不为jpg */
	String ILLEGAL_PICTURE_SUFFIX = "100051";

	/** 压缩包内找不到md文件 */
	String CANT_FIND_MD_FILE = "100052";

	/** 压缩包中md文件不能大于%dM */
	String MD_SIZE_TOO_LARGE = "100053";

	/** 未知场景 */
	String UNKNOWN_SCENE = "100054";

	/**
	 * 空白id
	 */
	String BLANK_ID = "100055";

	/** 文件名称过长*/
	String FILE_NAME_UPPER_LIMIT = "100056";

	/** 场景未启动*/
	String NOT_STARTED_SCENE = "100057";
}
