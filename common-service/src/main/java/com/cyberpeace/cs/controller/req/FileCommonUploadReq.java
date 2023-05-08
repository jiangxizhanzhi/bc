package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 常见文件上传要求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class FileCommonUploadReq implements Serializable {

	@ApiModelProperty("文件")
	private MultipartFile file;

	@ApiModelProperty("上传类型 ")
	private Integer type;

}
