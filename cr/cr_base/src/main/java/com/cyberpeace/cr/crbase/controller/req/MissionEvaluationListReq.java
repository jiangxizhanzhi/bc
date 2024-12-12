package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务评估列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Getter
@Setter
public class MissionEvaluationListReq extends PageReqBase {

	/**
	 * 房间id
	 */
	@NotNull
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
	private Integer evaluateType;

	/**
	 * 显示已评估
	 */
	@NotNull
	@ApiModelProperty(value = "只展示未评估")
	private Boolean isShowEvaluated;

}
