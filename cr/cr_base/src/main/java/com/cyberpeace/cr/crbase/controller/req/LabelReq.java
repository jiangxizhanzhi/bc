package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class LabelReq {

	/**
	 * 标签
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "雷达图维度")
 	@NotNull
	private List<String> labels;
 

}
