package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class TheoryImportReq implements Serializable {

	@ApiModelProperty("习题集编号")
	@NotBlank
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	@NotBlank
	private String categoryId;

	@ApiModelProperty("文件")
	@NotNull
	private MultipartFile file;

}
