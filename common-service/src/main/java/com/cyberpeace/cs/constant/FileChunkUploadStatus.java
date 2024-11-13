package com.cyberpeace.cs.constant;

import lombok.Data;

/**
 * 文件块上传状态
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public final class FileChunkUploadStatus {

	/**
	 * 未上传
	 */
	public static final Integer NOT_UPLOADED = 0;

	/**
	 * 成功
	 */
	public static final Integer SUCCESS = 1;

	/**
	 * 失败
	 */
	public static final Integer FAIL = 2;

	/**
	 * 持续
	 */
	public static final Integer ONGOING = 3;

}
