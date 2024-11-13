package com.cyberpeace.cs.service.context.upload;

import lombok.Data;

/**
 * 合并文件响应
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class MergeFileRsp {

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * tmpdir uuid
	 */
	private String tmpdirUuid;
}
