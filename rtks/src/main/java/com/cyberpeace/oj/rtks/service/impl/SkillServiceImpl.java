package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.dao.RelatedCourseDao;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.oj.rtks.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private RelatedCourseDao relatedCourseDao;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		for (SkillRes skillRes : skillResList) {
			String courses = skillRes.getCourses();

			List<RelatedCourse> relatedCourseList = relatedCourseDao.setCourseResult(courses);
			skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);
		}

		return PageUtil.getListResult(result);
	}

	@Override
	public SkillRes getSkillByResourceId(String id) {
		SkillRes skillRes = skillMapper.getSkillByResourceId(id);

		List<RelatedCourse> relatedCourseList = relatedCourseDao
				.setCourseResult(skillRes.getCourses());
		skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		skillRes.setCourseList(relatedCourseList);

		return skillRes;
	}
}
