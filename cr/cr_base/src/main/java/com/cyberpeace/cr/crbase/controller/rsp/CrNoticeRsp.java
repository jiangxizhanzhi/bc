package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 靶场通知响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CrNoticeRsp implements Serializable {

    /**
     * 上下文
     */
    @ApiModelProperty("公告内容")
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 岗位
     */
    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;

    /**
     * 在顶部
     */
    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 用户数据
     */
    @ApiModelProperty("用户信息")
    private UserData userData;

    /**
     * 创造用户id
     */
    private String createUserId;

    /**
     * 资源id
     */
    private String resourceId;
}
