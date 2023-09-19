package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势导调归属类型
 * @author myy
 */
@Data
public class SituationBelongDescRsp {

    @ApiModelProperty(value = "成功链接")
    private String redUrl;

    @ApiModelProperty(value = "失败类型")
    private String blueUrl;
}
