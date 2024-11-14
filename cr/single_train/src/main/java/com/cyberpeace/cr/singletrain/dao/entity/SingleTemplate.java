package com.cyberpeace.cr.singletrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 单个模板
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_single_template")
public class SingleTemplate extends BaseTable {
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 业务模板id
     */
    private String businessTemplateId;

    /**
     * 前置技能
     */
    private String preSkills;

    /**
     * 训练技能
     */
    private String trainSkills;

    /**
     * 子类型
     */// 类型字段使用?
    private Integer subType;

    /**
     * 持续时间
     */
    private Integer durationTime;

    /**
     * 任务计划
     */
    private String missionsScheme;

}
