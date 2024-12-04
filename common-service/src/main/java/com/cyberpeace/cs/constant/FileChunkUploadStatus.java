package com.cyberpeace.cs.constant;

/**
 * 文件块上传状态
 *
 * @author SN
 * @date 2024/11/11
 */
public interface FileChunkUploadStatus {

	/**
	 * 未上传
	 */
	Integer NOT_UPLOADED = 0;

	/**
	 * 成功
	 */
	Integer SUCCESS = 1;

	/**
	 * 失败
	 */
	Integer FAIL = 2;

	/**
	 * 持续
	 */
	Integer ONGOING = 3;

}
