package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTrainDepartmentReq extends PageReqBase {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("职称")
    private String title;

    @ApiModelProperty("级别")
    private String levelId;

    public void setLevel_id(String levelId) {
        this.levelId = levelId;
    }
}
