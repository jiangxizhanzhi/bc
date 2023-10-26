package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CrNoticeReq implements Serializable {

    /**
    * 
    */
    private Integer id;
    /**
    * uuid
    */
    @ApiModelProperty("uuid")
    private String resourceId;
    /**
    * 创建人id
    */
    @ApiModelProperty("创建人id")
    private String createUserId;
    /**
    * 公告内容
    */
    @ApiModelProperty("公告内容")
    private String context;
    /**
    * 公告对象
    */
    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;
    /**
    * 房间id
    */
    @ApiModelProperty("房间id")
    private String roomId;

    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    @ApiModelProperty("是否红蓝 0否 1是")
    private Boolean isRb;

}
