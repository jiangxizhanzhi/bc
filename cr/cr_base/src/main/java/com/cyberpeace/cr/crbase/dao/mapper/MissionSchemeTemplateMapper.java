package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;

@Mapper
@Repository
public interface MissionSchemeTemplateMapper extends BaseMapper<MissionSchemeTemplate> {

	IPage<MissionSchemeTemplate> list(Page page, String pattern);

	void batchInsert(
			@Param("missionSchemeTemplates") List<MissionSchemeTemplate> missionSchemeTemplates);

	Boolean existByTitle(@Param("title") String title);

}
