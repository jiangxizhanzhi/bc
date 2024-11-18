package com.cyberpeace.cr.crbase.service.context.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 技能分数上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillScoreContext implements Serializable {

    /**
     * 技能名称
     *
     * @see String
     */
    private String skillName;

    /**
     * 得分
     *
     * @see Integer
     */
    private Integer score;

    /**
     * 最高分
     *
     * @see Integer
     */
    private Integer maxScore;

    /**
     * id
     *
     * @see Integer
     */
    private Integer id;


}
