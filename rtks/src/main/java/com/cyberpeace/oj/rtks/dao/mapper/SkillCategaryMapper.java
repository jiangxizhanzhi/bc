package com.cyberpeace.oj.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategary;

@Mapper
@Repository
public interface SkillCategaryMapper extends BaseMapper<SkillCategary> {

	IPage<SkillCategary> listSkillCategary(Page page, @Param("pattern") String pattern);

}
