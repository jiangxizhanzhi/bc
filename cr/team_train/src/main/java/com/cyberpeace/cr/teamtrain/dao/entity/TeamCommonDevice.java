package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@TableName("cr_team_common_device")
@Data
public class TeamCommonDevice {
    @ApiModelProperty("自增ID")
    private Integer id;
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;
    @ApiModelProperty("房间id")
    private String roomId;
    @ApiModelProperty("设备id")
    private String deviceId;
    @ApiModelProperty("添加人")
    private String collectUserId;
    @ApiModelProperty("创建人")
    private String createUserId;
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
