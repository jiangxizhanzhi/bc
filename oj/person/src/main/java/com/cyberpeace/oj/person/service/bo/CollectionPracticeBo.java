package com.cyberpeace.oj.person.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收集练习Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class CollectionPracticeBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 习题类型
	 */
	@ApiModelProperty("习题类型")
	private Integer practiceType;

	/**
	 * 分类编号
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 分类名称
	 */
	@ApiModelProperty("分类名称")
	private String categoryName;

}
