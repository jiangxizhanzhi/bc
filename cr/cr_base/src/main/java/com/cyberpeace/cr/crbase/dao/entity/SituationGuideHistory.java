package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 态势导调历史记录表
 */
@Data
@TableName(value = "situation_guide_history")
public class SituationGuideHistory extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String sceneId;

    private String eventName;

    private Integer type;

    private Integer eventType;

    private Integer category;

    private String source;

    private String target;

    private Integer result;

    private Integer role;

    private String taskId;

    private Integer triggerCondition;

}