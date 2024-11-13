package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LabelReq {

	/**
	 * 标签
	 */
	@ApiModelProperty(value = "雷达图维度")
 	@NotNull
	private List<String> labels;
 

}
