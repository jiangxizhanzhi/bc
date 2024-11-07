package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 技能分数
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillScore implements Serializable {

    /**
     * 技能编号
     */
    private String skillId;

    /**
     * 得分
     */
    private String score;

}
