package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate
 */
@Mapper
@Repository
public interface TeamTemplateMapper extends BaseMapper<TeamTemplate> {

	IPage<TeamTemplateData> listTeamTemplate(Page<TeamTemplateData> page, ListQuery listQuery);

	TeamTemplate getByBusinessId(String resourceId);

	List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> ids);

	Boolean deleteByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	List<TeamTemplate> batchGetByBusinessId(List<String> businessTemplateIds);
}
