package com.cyberpeace.cr.tool.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("工具评分信息")
public class ToolMarkingReq implements Serializable {


	@ApiModelProperty("工具id")
	private String toolId;

	@ApiModelProperty("评价分数")
	private Integer score;



	public void validate() {
		// 校验分数最大值
		int scoreMax = 10;
		if (null != score ) {
			CrCommonValidator.validateRange(score,1, 10, ErrorCode.SCORE_RANGE_ERROR);
		} else {
			throw new BusinessException(ErrorCode.SCORE_RANGE_ERROR, 1, 10);
		}

	}

}
