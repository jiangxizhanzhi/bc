package com.cyberpeace.cr.crbase.service.context.score;

import lombok.Data;

import java.util.List;

@Data
public class UserSkillScoreContext {


    private List<SkillScoreContext> skillScoreContextList;

    private String userId;
}
