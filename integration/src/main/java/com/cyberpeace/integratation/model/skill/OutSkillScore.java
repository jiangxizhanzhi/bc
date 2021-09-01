package com.cyberpeace.integratation.model.skill;

import lombok.Data;

import java.io.Serializable;

@Data
public class OutSkillScore implements Serializable {

    /** 技能编号*/
    private String skillId;

    /** 得分*/
    private String score;

}
