package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class WebTheoryBo extends AdminTheoryBo {

	/**
	 * is 集合
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 解决详情
	 */
	@ApiModelProperty("解题信息")
	private PracticeNoSolvedBo resolvedDetail;

	/**
	 * 空白计数
	 */
	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	/**
	 * 解决
	 */
	@ApiModelProperty("解题状态：0=尝试过,1=已解出,2=未开始")
	private Integer solved;

	/**
	 * 实践设置名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

}
