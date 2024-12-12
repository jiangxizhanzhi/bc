package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 类别列表 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeCategoryBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class CategoryListBo extends PracticeCategoryBo {

	/**
	 * 分类列表 BOS
	 *
	 * @see List<CategoryListBo>
	 */
	@ApiModelProperty("子分类信息")
	private List<CategoryListBo> categoryListBos;

}
