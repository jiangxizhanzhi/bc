package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("公告详情")
public class CrNoticeDetailRsp implements Serializable {
    @ApiModelProperty("公告ID")
    private String resourceId;

    @ApiModelProperty("公告对象")
    private Integer group;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("是否置顶")
    private String isTop;

    @ApiModelProperty("附件id")
    private String attachmentId;

    @ApiModelProperty("附件信息")
    private AttachmentBo attachmentBo;
}
