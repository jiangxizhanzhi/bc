package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("训练公告分页查询条件")
public class CrNoticePageReq extends PageReqBase {
    @ApiModelProperty(value = "训练业务ID", required = true)
    private String roomId;
}
