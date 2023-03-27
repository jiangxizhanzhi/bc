package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("训练公告更新数据")
public class CrNoticeUpdateReq {
    @ApiModelProperty(value = "公告ID", required = true)
    private String resourceId;

    @ApiModelProperty("公告对象")
    private String group;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("是否置顶")
    private Boolean isTop;

    @ApiModelProperty("附件id")
    private String attachmentId;
}
