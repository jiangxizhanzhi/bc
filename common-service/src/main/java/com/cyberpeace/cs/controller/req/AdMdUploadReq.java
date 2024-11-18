package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 竞赛markdown上传请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AdMdUploadReq implements Serializable {

	/**
	 * 文件
	 *
	 * @see MultipartFile
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;
}
