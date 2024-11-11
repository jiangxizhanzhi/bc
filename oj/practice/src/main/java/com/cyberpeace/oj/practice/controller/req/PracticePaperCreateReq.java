package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 练习试卷创造请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticePaperCreateReq implements Serializable {

	/**
	 * 状态
	 */
	@ApiModelProperty("题目状态")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_STATUS_NOT_SELECTED)
	private List<Integer> status;

	/**
	 * 计数
	 */
	@ApiModelProperty("出题数量")
	private Integer count;

	/**
	 * 困难
	 */
	@ApiModelProperty("题目难度")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIFFICULTY_NOT_SELECTED)
	private List<Integer> difficulty;

	/**
	 * 类型
	 */
	@ApiModelProperty("题目类型")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_TYPE_NOT_SELECTED)
	private List<Integer> type;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向选择")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIRECTION_NOT_SELECTED)
	private List<String> direction;
}
