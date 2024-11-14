package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 理论导入请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class TheoryImportReq {

	/**
	 * 习题集编号
	 */
	@ApiModelProperty(name = "practice_set_id", value = "习题集编号")
	@NotBlank
	private String practiceSetId;

	/**
	 * 文件
	 */
	@ApiModelProperty("文件")
	@NotNull
	private MultipartFile file;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

}
