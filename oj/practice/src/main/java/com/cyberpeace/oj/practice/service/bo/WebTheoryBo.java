package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AdminTheoryBo
 * @since 1.0.0
 */
@Data
public class WebTheoryBo extends AdminTheoryBo {

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 解决详情
	 *
	 * @see PracticeNoSolvedBo
	 */
	@ApiModelProperty("解题信息")
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * 空白计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	/**
	 * 解决
	 *
	 * @see Integer
	 */
	@ApiModelProperty("解题状态：0=尝试过,1=已解出,2=未开始")
	private Integer solved;

	/**
	 * 实践设置名称
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
