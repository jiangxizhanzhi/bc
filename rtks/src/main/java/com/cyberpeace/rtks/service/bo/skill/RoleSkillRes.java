package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

/**
 * 岗位技能结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleSkillRes implements Serializable {

    /**
     * 岗位id
     *
     * @see String
     */
    private String roleId;

    /**
     * 技能计数
     *
     * @see Integer
     */
    private Integer skillCount;

    /**
     * 任务计数
     *
     * @see Integer
     */
    private Integer missionCount;
}
