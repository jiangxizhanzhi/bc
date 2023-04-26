package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProgressRes implements Serializable {

	@ApiModelProperty("方向名称")
	private String name;

	@ApiModelProperty("方向id")
	private String categoryId;

	@ApiModelProperty("已做题数")
	private Integer count;

	@ApiModelProperty("总题数")
	private Integer sum;

}
