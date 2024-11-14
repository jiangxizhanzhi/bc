package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class WebTheoryBo extends AdminTheoryBo {

	/**
	 * 是否收藏
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 解题信息
	 */
	@ApiModelProperty("解题信息")
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * 填空题空格数量
	 */
	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	/**
	 * 解题状态：0=尝试过,1=已解出,2=未开始
	 */
	@ApiModelProperty("解题状态：0=尝试过,1=已解出,2=未开始")
	private Integer solved;

	/**
	 * 习题集名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
