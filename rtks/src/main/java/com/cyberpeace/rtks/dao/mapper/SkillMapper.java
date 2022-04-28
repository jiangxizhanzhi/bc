package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillSimpleRes;

@Mapper
@Repository
public interface SkillMapper extends BaseMapper<Skill> {

	IPage<SkillRes> listSkill(Page page, PageReqBase skillListReq);

	SkillRes getSkillByResourceId(String id);

	List<SkillRes> getAllSkills(String scene_category);

	boolean existByResourceId(@Param("resourceId") String resourceId);

	IPage<SkillSimpleRes> pageSimple(@Param("page") Page page,
			@Param("reqBase") PageReqBase reqBase);
}
