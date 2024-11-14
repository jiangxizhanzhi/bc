package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordListReq extends PageReqBase {
	/**
	 * 房间Id
	 */
	@NotNull
    @ApiModelProperty("房间Id")
    private String taskId;

	/**
	 * 状态查询
	 */
	@ApiModelProperty("状态查询")
    private Integer status;
}
