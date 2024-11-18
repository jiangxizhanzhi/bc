package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 靶场通知响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CrNoticeRsp implements Serializable {

    /**
     * 上下文
     *
     * @see String
     */
    @ApiModelProperty("公告内容")
    private String context;

    /**
     * 创建时间
     *
     * @see Date
     */
    private Date createTime;

    /**
     * 角色
     *
     * @see Integer
     */
    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;

    /**
     * 在顶部
     *
     * @see Integer
     */
    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 用户数据
     *
     * @see UserData
     */
    @ApiModelProperty("用户信息")
    private UserData userData;

    /**
     * 创造用户id
     *
     * @see String
     */
    private String createUserId;

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}
