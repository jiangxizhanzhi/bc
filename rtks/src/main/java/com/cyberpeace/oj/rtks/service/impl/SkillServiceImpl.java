package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.dao.entity.SkillCourse;
import com.cyberpeace.oj.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.oj.rtks.service.SkillService;

/**
 *
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Override
	public ListRsp<Skill> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<Skill> result = skillMapper.listSkill(page, skillListReq);

		List<Skill> skillList = result.getRecords();
		for (Skill skill : skillList) {
			String s = skill.getCourses();

			try {
				List<SkillCourse> skillCourseList = JsonUtil.toJsonObjectArray(s,
						SkillCourse.class);
				for (SkillCourse skillCourse : skillCourseList) {
					skillCourse.setCourseName(getCourseNameById(skillCourse.getCourseId()));
				}
			} catch (Exception e) {
				;
			}
		}

		return PageUtil.getListResult(result);
	}

	private String getCourseNameById(String courseId) {
		// todo
		return null;
	}

	@Override
	public Skill getSkillByResourceId(String id) {
		Skill skill = skillMapper.getSkillByResourceId(id);

		return skill;
	}
}
