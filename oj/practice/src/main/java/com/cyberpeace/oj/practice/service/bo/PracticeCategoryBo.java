package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习类别Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PracticeCategoryBo implements Serializable {

	/**
	 * 习题分类名称
	 */
	@ApiModelProperty("习题分类名称")
    private String name;

	/**
	 * 习题分类编号
	 */
	@ApiModelProperty("习题分类编号")
    private String resourceId;

}
