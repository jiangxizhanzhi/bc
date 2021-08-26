package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import com.cyberpeace.rtks.controller.res.SkillRes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.dao.entity.Skill;

@Mapper
@Repository
public interface SkillMapper extends BaseMapper<Skill> {

	IPage<SkillRes> listSkill(Page page, SkillListReq skillListReq);

	SkillRes getSkillByResourceId(String id);

	List<String> filterIds(List<String> ids);
}
