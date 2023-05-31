package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_team_device")
public class TeamTrainDevice extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    private String deviceId;

    private String createUserId;
}
