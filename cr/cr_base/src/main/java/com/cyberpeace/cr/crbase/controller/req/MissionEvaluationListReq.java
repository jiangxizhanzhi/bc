package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务评价列表请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@ApiModel
@Data
public class MissionEvaluationListReq extends PageReqBase {

	@NotNull
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	@ApiModelProperty(value = "学员ID")
	private String studentHash;

	@ApiModelProperty(value = "评估方式")
	private Integer evaluateType;

	@NotNull
	@ApiModelProperty(value = "只展示未评估")
	private Boolean isShowEvaluated;

}
