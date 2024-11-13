package com.cyberpeace.cr.crbase.service.context.score;

import lombok.Data;

import java.util.List;

/**
 * 用户技能分数上下文
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class UserSkillScoreContext {


    /**
     * 技能分数上下文列表
     */
    private List<SkillScoreContext> skillScoreContextList;

    /**
     * 用户id
     */
    private String userId;
}
