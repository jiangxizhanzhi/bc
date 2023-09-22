package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class LoadResourceReq {

	@ApiModelProperty("数据文件")
	@NotNull
	private MultipartFile file;

}
