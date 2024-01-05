package com.cyberpeace.auth.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评项
 * @author SN
 * @date 2023/11/28
 */
@Data
public class OpLogAddReq  implements Serializable {



    @ApiModelProperty("模块")
    Integer module;

    @ApiModelProperty("操作类型")
    Integer method;

    @ApiModelProperty("操作对象")
    String target;

    @ApiModelProperty("详情")
    String detail;


}
