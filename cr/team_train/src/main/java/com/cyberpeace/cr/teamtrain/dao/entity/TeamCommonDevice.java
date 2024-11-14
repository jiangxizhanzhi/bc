package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队常用装置
 *
 * @author SN
 * @date 2024/11/14
 */
@TableName("cr_team_common_device")
@Data
public class TeamCommonDevice {
	/**
	 * 自增ID
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
	 * 设备id
	 */
	@ApiModelProperty("设备id")
    private String deviceId;
	/**
	 * 添加人
	 */
	@ApiModelProperty("添加人")
    private String collectUserId;
	/**
	 * 创建人
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
