package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 记录场景对实体靶标的占用
 *
 * @author SN
 */
@Data
@TableName("bs_device_occupation")
public class DeviceOccupation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 实体设备id
     */
    private String deviceId;

    /**
     * 实体设备类型
     */
    private Integer deviceType;

    /**
     * 设备所在场景id
     */
    private String sceneId;

    /**
     * 用户行为模拟id（仪表id）
     */
    private String simulatorId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
