package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 文件常见上传请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FileCommonUploadReq implements Serializable {

	/**
	 * 文件
	 *
	 * @see MultipartFile
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("上传类型 ")
	private Integer type;

}
