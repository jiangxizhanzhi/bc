package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class GuideRecordReq extends PageReqBase {

    @ApiModelProperty("房间id")
    @NotBlank
    private String roomId;

    @ApiModelProperty("训练类型 3:团队 4:红蓝")
    @NotNull
    private Integer businessType;

}
