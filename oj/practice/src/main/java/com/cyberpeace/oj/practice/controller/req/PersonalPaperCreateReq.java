package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class PersonalPaperCreateReq implements Serializable {

	@ApiModelProperty("题目状态")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_STATUS_NOT_SELECTED)
	private List<Integer> status;

	@ApiModelProperty("出题数量")
	private Integer count;

	@ApiModelProperty("题目难度")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIFFICULTY_NOT_SELECTED)
	private List<Integer> difficulty;

	@ApiModelProperty("题目类型")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_TYPE_NOT_SELECTED)
	private List<Integer> type;

	@ApiModelProperty("方向选择")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIRECTION_NOT_SELECTED)
	private List<String> direction;
}
