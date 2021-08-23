package com.cyberpeace.oj.rtks.controller.role;

import java.util.List;

import com.cyberpeace.oj.rtks.dao.entity.Ability;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.dao.entity.Task;

import lombok.Data;

@Data
public class RoleDetailOutput extends RoleOutput {

	private List<Task> taskDataList;

	private List<Knowledge> knowledgeDataList;

	private List<Skill> skillDataList;

	private List<Ability> abilityDataList;

}
