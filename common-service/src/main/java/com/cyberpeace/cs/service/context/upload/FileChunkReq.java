package com.cyberpeace.cs.service.context.upload;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 文件块请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FileChunkReq {

	/**
	 * md5
	 */
	private String md5;

	/**
	 * 文件
	 */
	private MultipartFile file;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 总
	 */
	private Integer total;

	/**
	 * 指数
	 */
	private Integer index;
}
