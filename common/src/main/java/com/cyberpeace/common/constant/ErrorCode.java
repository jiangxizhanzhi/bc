package com.cyberpeace.common.constant;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * 统一错误吗
 */
public enum ErrorCode implements BaseI18nErrorMsg {

	/**
	 * 参数校验异常
	 */
	METHOD_ARG_NOT_VALID("000001", "", ""),

	/**
	 * 数据库主键重复
	 */
	DUPLICATE_KEY("000002", "已存在", "already exist."),

	/**
	 * 通过参数中的resource_id无法找到对应资源
	 */
	RESOURCE_NOT_FOUND("000003", "", ""),

	INTERNAL_ERROR("099999", "", ""),

	// 文件上传失败
	FILE_UPLOAD_FAIL("100011", "文件上传失败", ""),

	/**
	 * 文件拷贝失败
	 */
	FILE_COPY_FAIL("100012", "文件复制失败", "File copy failure."),

	/**
	 * 文件压缩失败
	 */
	FILE_ZIP_FAIL("100013", "文件压缩失败", ""),

	/**
	 * 文件类型不正确
	 */
	FILE_TYPE_ERROR("100014", "文件类型错误", "File type error."),

	/**
	 * 文件读取失败
	 */
	FILE_READ_FAIL("100015", "文件读取失败", ""),

	/**
	 * 用户不在列表中
	 */
	USER_NOT_IN_LIST("100016", "", ""),

	/**
	 * 对应文件不存在
	 */
	FILE_NOT_EXIST("100017", "文件不存在", "File not exist."),

	/**
	 * 子类未实现
	 */
	SUB_NOT_IMPLEMENT("100018", "", ""),

	/**
	 * 类型错误
	 */
	TYPE_ERROR("100019", "", ""),

	REMOTE_SERVICE_EXCEPTION("010000", "远程服务异常。%s", "Remote service error.%s"),

	NOT_PAUSED("101035", "非暂停状态不能执行恢复操作", "Scene is not paused"),

	FAILED_TO_SAVE_FILE("", "保存文件失败", ""),

	FAILED_TO_COPY_FILE("", "复制文件失败", ""),

	FAILED_TO_OBTAIN_OFFICE_FILE_INFO("", "", "Failed to obtain office file information！"),

	BUSINESS_ERROR("", "", ""),

	SCENE_RUNNING_FAILED("100020", "场景启动失败", "Scene startup failure."),

	FILE_CONTENT_INVALID("100021", "文件读取失败，请检查文件内容是否有效。", "File content invalid.");


	@Getter
	private final String chinese;

	@Getter
	private final String english;

	@Getter
	private final String errorCode;

	ErrorCode(String errorCode, String chinese, String english) {
		this.errorCode = errorCode;
		this.chinese = chinese;
		this.english = english;
	}
}
