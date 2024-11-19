package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中台认证许可
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("用户菜单权限")
public class MpAuthPermission implements Serializable {
    /**
     * PK
     *
     * @see Integer
     */
    private Integer pk;

    /**
     * id
     *
     * @see String
     */
    private String id;

    /**
     * 创建时间
     *
     * @see Date
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    private Date updateTime;

    /**
     * 父级菜单
     *
     * @see String
     */
    @ApiModelProperty("父级菜单")
    private String parent;

    /**
     * 菜单标识符
     *
     * @see String
     */
    @ApiModelProperty("菜单标识符")
    private String identifier;

    /**
     * 租客id
     *
     * @see String
     */
    private String tenant;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 路径
     *
     * @see String
     */
    private String path;

    /**
     * 类型
     *
     * @see String
     */
    private String type;

    /**
     * 菜单是否可见
     *
     * @see String
     */
    @ApiModelProperty("菜单是否可见")
    private String visible;
}
