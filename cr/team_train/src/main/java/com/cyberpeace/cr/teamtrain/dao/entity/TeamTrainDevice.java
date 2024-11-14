package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 团队训练装置
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_team_device")
public class TeamTrainDevice extends BaseTable {
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 装置id
     */
    private String deviceId;

    /**
     * 创建用户id
     */
    private String createUserId;
}
