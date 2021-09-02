package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 同理论基础查询条件, 关闭`questionType`参数
 */
@Data
@ApiModel
public class PracticeOperationListReq extends PracticeTheoryListReq {

    @ApiModelProperty(hidden = true)
    private final Integer questionType;
}
