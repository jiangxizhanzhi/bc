package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import lombok.Data;

import java.util.List;

@Data
public class SkillRes extends Skill {

	private String system;

	private String category;

	private List<RelatedCourse> courseList;

}