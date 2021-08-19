package com.cyberpeace.oj.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;

@Mapper
@Repository
public interface SkillMapper extends BaseMapper<Skill> {

	IPage<SkillRes> listSkill(Page page, SkillListReq skillListReq);

	SkillRes getSkillByResourceId(String id);
}
