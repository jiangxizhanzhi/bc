package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 评估记录列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordListReq extends PageReqBase {
    /**
     * 任务id
     */
    @NotNull
    @ApiModelProperty("房间Id")
    private String taskId;

    /**
     * 状态
     */
    @ApiModelProperty("状态查询")
    private Integer status;
}
