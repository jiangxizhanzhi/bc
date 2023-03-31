package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 任务关联节点动效表
 */
@Data
@TableName("cr_mission_event_device")
public class MissionEventDevice extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 任务id
     */
    private String missionId;

    /**
     * 场景节点id
     */
    private String deviceId;
}
