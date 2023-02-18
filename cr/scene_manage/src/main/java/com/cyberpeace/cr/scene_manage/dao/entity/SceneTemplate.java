package com.cyberpeace.cr.scene_manage.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_scene_manage")
public class SceneTemplate extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String businessTemplateId;

    private String preSkills;

    private String trainSkills;

    // 类型字段使用?
    private Integer subType;

    private Integer durationTime;

    private String missionsScheme;

}
