package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广告医学博士上传请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AdMdUploadReq implements Serializable {

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	private MultipartFile file;
}
