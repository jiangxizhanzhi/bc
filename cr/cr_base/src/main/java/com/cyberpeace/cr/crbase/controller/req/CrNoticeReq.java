package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;


/**
 * 靶场通知请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrNoticeReq implements Serializable {

    /**
     * id
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

    /**
     * 在顶部
     */
    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 是 RB
     */
    @ApiModelProperty("是否红蓝 0否 1是")
    private Boolean isRb;

}
