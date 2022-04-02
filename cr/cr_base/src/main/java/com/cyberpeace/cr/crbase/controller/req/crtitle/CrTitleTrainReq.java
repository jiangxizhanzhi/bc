package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTitleTrainReq extends PageReqBase {
    @ApiModelProperty("训练类型")
    private Integer businessType;

    public void setBusiness_type(Integer businessType) {
        this.businessType = businessType;
    }
}
