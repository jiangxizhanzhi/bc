package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 评估记录列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
public class EvalRecordListReq extends PageReqBase {
    /**
     * 任务id
     *
     * @see String
     */
    @NotNull
    @ApiModelProperty("房间Id")
    private String taskId;

    /**
     * 状态
     *
     * @see Integer
     */
    @ApiModelProperty("状态查询")
    private Integer status;
}
