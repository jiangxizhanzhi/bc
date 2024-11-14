package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 自由查询结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class LibSelectRes implements Serializable {

	/**
	 * 习题集数据
	 */
	@ApiModelProperty("习题集数据")
    private List<PracticeSetRes> practiceSet;

	/**
	 * 分类数据
	 */
	@ApiModelProperty("分类数据")
    private List<CategoryListBo> category;

	/**
	 * 题目类型 0=单选 1=多选....
	 */
	@ApiModelProperty("题目类型 0=单选 1=多选....")
    private List<Integer> questionType;
}
