package com.cyberpeace.cr.crbase.service.context.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillScoreContext implements Serializable {

    private String skillName;

    private Integer score;

    private Integer maxScore;

    private Integer id;


}
