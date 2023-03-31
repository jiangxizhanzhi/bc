package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

/**
 * 任务关联节点动效表
 */
@TableName("cr_mission_event_device")
public class MissionEventDevice extends BaseTable {
    /**
     * 任务id
     */
    private String missionId;

    /**
     * 场景节点id
     */
    private String deviceId;
}
