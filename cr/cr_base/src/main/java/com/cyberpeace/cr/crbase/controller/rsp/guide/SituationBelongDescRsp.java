package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势导调归属类型
 *
 * @author myy@date 2024/11/12
 */
@Data
public class SituationBelongDescRsp {

    /**
     * 红色 URL
     */
    @ApiModelProperty(value = "成功链接")
    private String redUrl;

    /**
     * 蓝色 URL
     */
    @ApiModelProperty(value = "失败类型")
    private String blueUrl;
}
