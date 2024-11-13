package com.cyberpeace.cs.controller.req;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件常见上传请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FileCommonUploadReq {

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;

	/**
	 * 类型
	 */
	@ApiModelProperty("上传类型 ")
	private Integer type;
}
