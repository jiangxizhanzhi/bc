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
 * @date 2024/11/08
 */
@Data
@ApiModel("用户菜单权限")
public class MpAuthPermission implements Serializable {
    /**
     * PK
     */
    private Integer pk;

    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 父母
     */
    @ApiModelProperty("父级菜单")
    private String parent;

    /**
     * 标识符
     */
    @ApiModelProperty("菜单标识符")
    private String identifier;

    /**
     * 房客
     */
    private String tenant;

    /**
     * 名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 类型
     */
    private String type;

    /**
     * 可见
     */
    @ApiModelProperty("菜单是否可见")
    private String visible;
}
