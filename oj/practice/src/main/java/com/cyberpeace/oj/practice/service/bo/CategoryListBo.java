package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类别列表 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class CategoryListBo extends PracticeCategoryBo {

	/**
	 * 分类列表 BOS
	 */
	@ApiModelProperty("子分类信息")
	private List<CategoryListBo> categoryListBos;

}
