package com.cyberpeace.oj.person.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 集合练习 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class CollectionPracticeBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 练习类型
	 */
	@ApiModelProperty("习题类型")
	private Integer practiceType;

	/**
	 * 类别id
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 类别名称
	 */
	@ApiModelProperty("分类名称")
	private String categoryName;

}
