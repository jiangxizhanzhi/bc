package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class LabelReq {

	@ApiModelProperty(value = "雷达图维度")
	@NotNull
	private List<String> labels;

}
