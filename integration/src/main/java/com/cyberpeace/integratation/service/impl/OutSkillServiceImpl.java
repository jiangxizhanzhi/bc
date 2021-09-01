package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.integratation.model.skill.OutSkillScore;
import com.cyberpeace.integratation.service.OutSkillService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutSkillServiceImpl implements OutSkillService {

    private String SKILL_SCORE_API;

    @PostConstruct
    public void init() {
        SKILL_SCORE_API = ConfigUtil.getContextProperty("user.skill.score.list");
    }

    @Override
    public List<OutSkillScore> getUserSkillScore(String userId, List<String> skillIds) {
        // TODO 调用外部接口获取得分

        // 目前没接口 先模拟
        List<OutSkillScore> scoreList = skillIds.stream().map(skillId -> {
            OutSkillScore score = new OutSkillScore();
            score.setSkillId(skillId);
            score.setScore(String.valueOf(MathUtils.randomInt(100)));
            return score;
        }).collect(Collectors.toList());
        return scoreList;
    }
}
