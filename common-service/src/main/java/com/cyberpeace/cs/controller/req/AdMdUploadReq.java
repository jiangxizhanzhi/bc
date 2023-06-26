package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdMdUploadReq implements Serializable {

	@ApiModelProperty("文件")
	private MultipartFile file;
}
