package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 加载资源请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LoadResourceReq {

	/**
	 * 数据文件
	 */
	@ApiModelProperty("数据文件")
	@NotNull
	private MultipartFile file;

}
