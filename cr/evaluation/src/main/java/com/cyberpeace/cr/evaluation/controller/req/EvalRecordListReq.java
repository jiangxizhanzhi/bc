package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EvalRecordListReq extends PageReqBase {
    @NotNull
    @ApiModelProperty("房间Id")
    private String taskId;

    @ApiModelProperty("状态查询")
    private Integer status;
}
