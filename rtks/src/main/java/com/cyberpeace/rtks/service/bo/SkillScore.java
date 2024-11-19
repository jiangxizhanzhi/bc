package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 技能分数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SkillScore implements Serializable {

    /**
     * 技能编号
     *
     * @see String
     */
    private String skillId;

    /**
     * 得分
     *
     * @see String
     */
    private String score;

}
