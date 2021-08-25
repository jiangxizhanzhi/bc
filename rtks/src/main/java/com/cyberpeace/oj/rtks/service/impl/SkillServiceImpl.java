package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.oj.rtks.service.RelatedCourseService;
import com.cyberpeace.oj.rtks.service.SkillService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
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
	private RelatedCourseService relatedCourseDao;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		List<Skill> skillList = Lists.newArrayList();

		for (SkillRes skillRes : skillResList) {
			String courses = skillRes.getCourses();

			List<RelatedCourse> relatedCourseList = Lists.newArrayList();
			if (relatedCourseDao.setCourseResult(courses, relatedCourseList)) {
				Skill skill = new Skill();
				BeanUtils.copyProperties(skillRes, skill);
				skillList.add(skill);
			}
			skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);

		}
		updateBatchById(skillList);

		return PageUtil.getListResult(result);
	}

	@Override
	public SkillRes getSkillByResourceId(String id) {
		SkillRes skillRes = skillMapper.getSkillByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();

		if (relatedCourseDao.setCourseResult(skillRes.getCourses(), relatedCourseList)) {
			Skill skill = new Skill();
			BeanUtils.copyProperties(skillRes, skill);
			updateById(skill);
		} ;

		skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		skillRes.setCourseList(relatedCourseList);

		return skillRes;
	}

	@Override
	public List<String> filterIds(List<String> ids) {
		return skillMapper.filterIds(ids);
	}
}
