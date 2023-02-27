package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTitleDepartmentReq extends PageReqBase {

    @ApiModelProperty("姓名")
    private String name;
}
