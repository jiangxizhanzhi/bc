package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队通用设备
 *
 * @author SN
 * @date 2024/11/07
 */
@TableName("cr_team_common_device")
@Data
public class TeamCommonDevice {
    /**
     * id
     */
    @ApiModelProperty("自增ID")
    private Integer id;
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private String roomId;
    /**
     * 装置id
     */
    @ApiModelProperty("设备id")
    private String deviceId;
    /**
     * 收集用户id
     */
    @ApiModelProperty("添加人")
    private String collectUserId;
    /**
     * 创造用户id
     */
    @ApiModelProperty("创建人")
    private String createUserId;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
