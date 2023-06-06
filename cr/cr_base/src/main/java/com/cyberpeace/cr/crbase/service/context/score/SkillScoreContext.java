package com.cyberpeace.cr.crbase.service.context.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillScoreContext {

    private String skillName;

    private Integer score;

    private Integer id;


}
