package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 标签请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class LabelReq {

	/**
	 * 标签
	 */
	@ApiModelProperty(value = "雷达图维度")
 	@NotNull
	private List<String> labels;
 

}
