package com.cyberpeace.cr.crbase.service.context.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 技能分数上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillScoreContext implements Serializable {

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 最高分
     */
    private Integer maxScore;

    /**
     * id
     */
    private Integer id;


}
