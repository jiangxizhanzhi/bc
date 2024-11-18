package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估查询体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
public class MissionEvaluationQuery extends PageReqBase {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 学员散 列
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	/**
	 * 评估类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评估方式")
	private String evaluateType;

	/**
	 * 评估状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

}
