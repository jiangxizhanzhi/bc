package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类别列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class CategoryListBo extends PracticeCategoryBo {

	/**
	 * 子分类信息
	 */
	@ApiModelProperty("子分类信息")
	private List<CategoryListBo> categoryListBos;

}
