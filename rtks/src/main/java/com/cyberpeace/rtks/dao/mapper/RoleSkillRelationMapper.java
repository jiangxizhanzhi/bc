package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;

@Mapper
@Repository
public interface RoleSkillRelationMapper extends BaseMapper<RoleSkillRelation> {

	void deleteBySkillIds(@Param("skillIds") List<String> skillIds);
}
