package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("训练公告创建数据")
public class CrNoticeCreateReq implements Serializable {

    @ApiModelProperty(value = "训练实例ID", required = true)
    private String roomId;

    @ApiModelProperty(value = "公告对象", required = true)
    private String group;

    @ApiModelProperty(value = "公告内容", required = true)
    private String content;

    @ApiModelProperty(value = "是否置顶", required = true)
    private Boolean isTop;

    @ApiModelProperty(value = "附件id")
    private String attachmentId;
}
