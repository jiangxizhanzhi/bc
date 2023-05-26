package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CrNoticeRsp implements Serializable {

    @ApiModelProperty("公告内容")
    private String context;

    private Date createTime;

    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;

    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    @ApiModelProperty("用户信息")
    private UserData userData;

    private String createUserId;
}
