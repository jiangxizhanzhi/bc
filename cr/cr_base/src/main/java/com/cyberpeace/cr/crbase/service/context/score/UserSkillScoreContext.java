package com.cyberpeace.cr.crbase.service.context.score;

import lombok.Data;

import java.util.List;

/**
 * 用户技能分数上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class UserSkillScoreContext {


    /**
     * 技能分数上下文列表
     *
     * @see List<SkillScoreContext>
     */
    private List<SkillScoreContext> skillScoreContextList;

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;
}
