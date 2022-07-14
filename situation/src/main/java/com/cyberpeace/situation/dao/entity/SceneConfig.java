package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("situation_scene_config")
public class SceneConfig {

    @TableId(type = IdType.UUID,value = "resource_id")
    private String resourceId;

    private String sceneId;

    private String schemeCode;
}
