package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate
 */
@Mapper
@Repository
public interface TeamTemplateMapper extends BaseMapper<TeamTemplate> {

	IPage<TeamTemplateData> listTeamTemplate(Page page, ListQuery listQuery);

	TeamTemplate getByBusinessId(String resourceId);
}
