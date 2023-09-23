package com.cyberpeace.cs.controller.req;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdMdUploadReq {

	@ApiModelProperty("文件")
	private MultipartFile file;
}
