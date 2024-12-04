package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 实践纸创造请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PracticePaperCreateReq implements Serializable {

	/**
	 * 状态
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("题目状态")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_STATUS_NOT_SELECTED)
	private List<Integer> status;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("出题数量")
	private Integer count;

	/**
	 * 难度
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("题目难度")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIFFICULTY_NOT_SELECTED)
	private List<Integer> difficulty;

	/**
	 * 类型
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty("题目类型")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_TYPE_NOT_SELECTED)
	private List<Integer> type;

	/**
	 * 方向
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("方向选择")
	@Size(min = 1, message = OjBusinessErrorCode.QUESTION_DIRECTION_NOT_SELECTED)
	private List<String> direction;
}
