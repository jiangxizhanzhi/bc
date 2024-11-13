package com.cyberpeace.cs.service.context.upload;

import lombok.Data;

/**
 * 文件合并请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FileMergeReq {

	/**
	 * 总
	 */
	private Integer total;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * md5
	 */
	private String md5;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 文件类型
	 */
	private String fileType;
}
