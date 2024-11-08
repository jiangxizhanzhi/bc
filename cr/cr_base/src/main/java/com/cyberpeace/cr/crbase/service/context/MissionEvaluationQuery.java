package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评估查询体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionEvaluationQuery extends PageReqBase {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 学员散 列
	 */
	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	/**
	 * 评估类型
	 */
	@ApiModelProperty(value = "评估方式")
	private String evaluateType;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

}
