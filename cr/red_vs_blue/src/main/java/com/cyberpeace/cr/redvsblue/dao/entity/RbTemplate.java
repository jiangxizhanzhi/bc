package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * RB 模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_rb_template")
public class RbTemplate extends BaseTable {

    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

    /**
     * 子类型
     *
     * @see Integer
     */
    private Integer subType;

    /**
     * 持续时间
     *
     * @see Integer
     */
    private Integer durationTime;

    /**
     * 预技能
     *
     * @see String
     */
    private String preSkill;

    /**
     * 训练技能
     *
     * @see String
     */
    private String trainSkill;

    /**
     * 角色配置
     *
     * @see String
     */
    private String roleConfig;

    /**
     * 业务模板id
     *
     * @see String
     */
    private String businessTemplateId;

}
