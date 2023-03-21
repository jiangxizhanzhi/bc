package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("训练公告创建数据")
public class CrNoticeReq implements Serializable {
    @ApiModelProperty("公告ID")
    private String resourceId;

    @ApiModelProperty("训练业务ID")
    private String businessId;

    @ApiModelProperty(value = "训练业务类型")
    private Integer businessType;

    @ApiModelProperty("公告对象")
    private Integer group;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("是否置顶")
    private String isTop;

    @ApiModelProperty("附件id")
    private String attachmentId;
}
