package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("训练公告分页数据")
public class NoticeListRsp implements Serializable {

    @ApiModelProperty("公告ID")
    private String resourceId;

    @ApiModelProperty("训练业务ID")
    private String businessId;

    @ApiModelProperty("公告内容")
    private String content;

    @ApiModelProperty("公告对象")
    private Integer group;

    @ApiModelProperty("是否置顶")
    private Boolean isTop;

    @ApiModelProperty("发布时间")
    private Date releaseTime;

    @ApiModelProperty("附件")
    private AttachmentBo attachmentBo;

}
