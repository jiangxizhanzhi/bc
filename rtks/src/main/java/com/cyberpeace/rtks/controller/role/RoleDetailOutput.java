package com.cyberpeace.rtks.controller.role;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.Task;

import lombok.Data;

@Data
public class RoleDetailOutput extends RoleOutput {

	private List<Task> taskDataList;

	private List<Knowledge> knowledgeDataList;

	private List<Skill> skillDataList;

	private List<Ability> abilityDataList;

}
