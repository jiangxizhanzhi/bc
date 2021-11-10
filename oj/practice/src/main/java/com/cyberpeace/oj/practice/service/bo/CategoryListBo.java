package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CategoryListBo extends PracticeCategoryBo {

	@ApiModelProperty("子分类信息")
	private List<CategoryListBo> categoryListBos;

}
