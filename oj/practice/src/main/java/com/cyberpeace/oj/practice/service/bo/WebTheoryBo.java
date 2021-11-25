package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebTheoryBo extends AdminTheoryBo {

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("解题信息")
	private PracticeNoSolvedBo resolvedDetail;

	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

}
