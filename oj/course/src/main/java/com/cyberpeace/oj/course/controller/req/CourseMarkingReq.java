package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程评分信息")
public class CourseMarkingReq implements Serializable {


	@ApiModelProperty("课程id")
	private String courseId;

	@ApiModelProperty("评价分数")
	private Integer score;



	public void validate() {
		// 校验分数最大值
		int scoreMax = 10;
		if (null != score ) {
			CrCommonValidator.validateRange(score,1, 10, ErrorCode.SCORE_RANGE_ERROR);
		}

	}

}
