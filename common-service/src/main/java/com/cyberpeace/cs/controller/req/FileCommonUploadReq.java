package com.cyberpeace.cs.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileCommonUploadReq implements Serializable {

	@ApiModelProperty("文件")
	private MultipartFile file;

	@ApiModelProperty("上传类型 ")
	private Integer type;

}
