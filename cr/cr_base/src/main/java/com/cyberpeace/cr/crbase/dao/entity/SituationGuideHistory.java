package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 态势导调历史记录表
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName(value = "situation_guide_history")
public class SituationGuideHistory extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 导调id
     */
    private String guideId;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 事件类型
     */
    private Integer eventType;

    /**
     * 类别
     */
    private Integer category;

    /**
     * 源
     */
    private String source;

    /**
     * 目标
     */
    private String target;

    /**
     * 结果
     */
    private Integer result;

    /**
     * 岗位
     */
    private Integer role;

    /**
     * 任务id
     */
    private String missionId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 触发条件
     */
    private Integer triggerCondition;

}