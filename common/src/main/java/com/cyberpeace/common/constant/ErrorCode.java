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
	String FILE_TYPE_ERROR = "10014";
}
