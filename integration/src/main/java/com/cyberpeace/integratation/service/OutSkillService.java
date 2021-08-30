package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.skill.OutSkillScore;

import java.util.List;

public interface OutSkillService {

    /**
     * 查询用户技能得分
     * @param userId 用户编号
     * @param skillIds 技能编号
     * @return
     */
    List<OutSkillScore> getUserSkillScore(String userId, List<String> skillIds);

}
