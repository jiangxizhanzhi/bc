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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_single_template")
public class SingleTemplate extends BaseTable {
    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 业务模板id
     *
     * @see String
     */
    private String businessTemplateId;

    /**
     * 预科技能
     *
     * @see String
     */
    private String preSkills;

    /**
     * 训练技能
     *
     * @see String
     */
    private String trainSkills;

    /**
     * 子类型
     *
     * @see Integer
     */ // 类型字段使用?
    private Integer subType;

    /**
     * 持续时间
     *
     * @see Integer
     */
    private Integer durationTime;

    /**
     * 任务计划
     *
     * @see String
     */
    private String missionsScheme;

}
