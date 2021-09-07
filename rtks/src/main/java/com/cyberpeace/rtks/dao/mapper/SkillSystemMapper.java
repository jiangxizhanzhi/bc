package com.cyberpeace.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.SkillSystem;

@Mapper
@Repository
public interface SkillSystemMapper extends BaseMapper<SkillSystem> {

	IPage<SkillSystem> listSkillSystem(Page page, @Param("pattern") String pattern);

	void updateSceneSkillSystem(String skillSystemId, Integer sceneId);

	void updateDefaultSceneSkillSystem();
}
