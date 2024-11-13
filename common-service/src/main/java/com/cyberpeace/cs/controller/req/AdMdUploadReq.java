package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广告Markdown上传请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class AdMdUploadReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;
}
