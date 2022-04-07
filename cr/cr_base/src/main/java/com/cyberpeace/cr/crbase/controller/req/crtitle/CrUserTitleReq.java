package com.cyberpeace.cr.crbase.controller.req.crtitle;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrUserTitleReq extends PageReqBase {
    @ApiModelProperty("称号等级id")
    private String levelId;

    @ApiModelProperty("称号名称，文本精确匹配")
    private String title;

    public void setLevel_id(String levelId) {
        this.levelId = levelId;
    }
}
