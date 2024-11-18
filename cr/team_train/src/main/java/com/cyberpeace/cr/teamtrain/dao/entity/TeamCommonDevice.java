package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队通用设备
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@TableName("cr_team_common_device")
@Data
public class TeamCommonDevice {
    /**
     * id
     *
     * @see Integer
     */
    @ApiModelProperty("自增ID")
    private Integer id;
    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;
    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("房间id")
    private String roomId;
    /**
     * 装置id
     *
     * @see String
     */
    @ApiModelProperty("设备id")
    private String deviceId;
    /**
     * 收集用户id
     *
     * @see String
     */
    @ApiModelProperty("添加人")
    private String collectUserId;
    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人")
    private String createUserId;
    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
