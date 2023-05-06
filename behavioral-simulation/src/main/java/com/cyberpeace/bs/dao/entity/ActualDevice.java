package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 实体设备
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
@TableName("bs_actual_device")
public class ActualDevice {

    private Integer id;

    /**
     * 设备id，与龙测同步
     */
    @TableId(type = IdType.INPUT, value = "device_id")
    private String deviceId;

    /**
     * 设备类型
     */
    private Integer deviceType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 实体设备在虚实结合交换机上的端口号
     */
    private String port;

    /**
     * 设备信息入库的时间
     */
    private Date createTime;
}
